package pages.gmail;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

public class LoginPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@type='email']")
    public WebElement emailTextInput;

    @FindBy(xpath = "//input[@type='password']")
    public WebElement passwordTextInput;

    @FindBy(xpath = "//input[@name='totpPin']")
    public WebElement totpInputText;

    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement totpDontAskAgainCheckbox;

    @FindBy(xpath = "//*[contains(text(), 'Wrong password')]")
    public WebElement wrongPasswordErrorText;

    @FindBy(xpath = "//*[contains(text(), 'Wrong code')]")
    public WebElement wrongTOTPErrorText;

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailTextInput));
        emailTextInput.sendKeys(email);
        emailTextInput.sendKeys(Keys.RETURN);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordTextInput));
        passwordTextInput.sendKeys(password);
        passwordTextInput.sendKeys(Keys.RETURN);
    }

    public boolean isTotpTextInputVisible() {
        try {
            return totpInputText.isDisplayed();
        } catch (NoSuchElementException e) {
            LOG.warn("TOTP input is not seen");
            return false;
        }
    }

    public void enterTotp(String totp, boolean dontAskAgainOnThisDevice) {
        wait.until(ExpectedConditions.visibilityOf(totpInputText));
        if (dontAskAgainOnThisDevice) {
            totpDontAskAgainCheckbox.click();
        }
        totpInputText.sendKeys(totp);
        totpInputText.sendKeys(Keys.RETURN);
    }

    public boolean isLoginFailedDueToWrongPassword() {
        try {
            return wrongPasswordErrorText.isDisplayed();
        } catch (NoSuchElementException e) {
            LOG.info("Error text for login failure due to wrong password is not seen", e);
            return false;
        }
    }

    public boolean isLoginFailedDueToWrongTOTP() {
        try {
            return wrongTOTPErrorText.isDisplayed();
        } catch (NoSuchElementException e) {
            LOG.info("Error text for login failure due to wrong TOTP is not seen", e);
            return false;
        }
    }

}
