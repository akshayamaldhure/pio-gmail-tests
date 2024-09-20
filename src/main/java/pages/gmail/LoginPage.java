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

    @FindBy(xpath = "//*[contains(text(), 'Wrong password')]")
    public WebElement wrongPasswordErrorText;

    public void enterEmail(String email) {
        emailTextInput.sendKeys(email);
        emailTextInput.sendKeys(Keys.RETURN);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordTextInput));
        passwordTextInput.sendKeys(password);
        passwordTextInput.sendKeys(Keys.RETURN);
    }

    public boolean isLoginFailure() {
        try {
            return wrongPasswordErrorText.isDisplayed();
        } catch (NoSuchElementException nsee) {
            LOG.warn("Error text for login failure is not seen", nsee);
            return false;
        }
    }

}
