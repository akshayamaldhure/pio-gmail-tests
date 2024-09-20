package pages.gmail;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.List;

public class HomePage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@aria-label='Main menu']")
    public WebElement hamburgerMenu;

    @FindBy(xpath = "//div[contains(text(),'Compose')]")
    public WebElement composeEmailButton;

    @FindBy(xpath = "//input[@aria-label='To recipients']")
    public WebElement recipientsInputText;

    @FindBy(xpath = "//input[@name='subjectbox']")
    public WebElement subjectInputText;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    public WebElement emailBodyInputText;

    @FindBy(xpath = "//div[@data-tooltip='More options']")
    public WebElement moreOptionsMenu;

    @FindBy(xpath = "//div[contains(text(),'Label')]")
    public WebElement labelMenu;

    @FindBy(xpath = "//div[@role='button' and contains(@data-tooltip,'Send')]")
    public WebElement sendEmailButton;

    @FindBy(xpath = "//span[contains(text(),'Message sent')]")
    public WebElement emailSuccessPopupText;

    @FindBy(xpath = "//div[contains(@data-tooltip,'Messages from social networks')]")
    public WebElement socialTab;

    @FindBy(xpath = "//div[contains(text(),'Your Social tab is empty')]")
    public WebElement socialTabEmptyText;

    public void clickComposeEmail() {
        composeEmailButton.click();
    }

    public void enterRecipientEmailAddresses(List<String> emailAddresses) {
        emailAddresses.forEach(emailAddress -> {
            recipientsInputText.sendKeys(emailAddress);
            recipientsInputText.sendKeys(", ");
        });
    }

    public void enterSubject(String subject) {
        subjectInputText.sendKeys(subject);
    }

    public void enterBody(String body) {
        emailBodyInputText.sendKeys(body);
    }

    public void clickMoreOptionsMenu() {
        moreOptionsMenu.click();
    }

    public void clickOnLabelMenu() {
        labelMenu.click();
    }

    public void selectCategory(String category) {
        driver.findElement(By.xpath("//div[@role='menuitemcheckbox' and @title='" + category + "']")).click();
    }

    public void clickSendEmailButton() {
        wait.until(ExpectedConditions.elementToBeClickable(sendEmailButton));
        sendEmailButton.click();
    }

    public void switchToSocialTab() {
        wait.until(ExpectedConditions.elementToBeClickable(socialTab));
        socialTab.click();
    }

    public void starLatestEmailBySenderAndSubject(String senderName, String subject) {
        String starButtonXPath = "//tr[.//span[contains(text(), '" + senderName + "')] and .//span[contains(text(), '" + subject + "')]]//span[@aria-label='Not starred']";
        List<WebElement> starButtons = driver.findElements(By.xpath(starButtonXPath));
        if (starButtons.isEmpty()) {
            throw new IllegalArgumentException("Could not find any clickable Star button for the sender with name " + senderName + " and subject " + subject);
        }
        wait.until(ExpectedConditions.elementToBeClickable(starButtons.get(0)));
        starButtons.get(0).click();
    }

    public boolean isLatestEmailBySenderAndSubjectStarred(String senderName, String subject) {
        String starButtonXPath = "//tr[.//span[contains(text(), '" + senderName + "')] and .//span[contains(text(), '" + subject + "')]]//span[@aria-label='Starred']";
        List<WebElement> starButtons = driver.findElements(By.xpath(starButtonXPath));
        if (starButtons.isEmpty()) {
            throw new IllegalArgumentException("Could not find any starred email for the sender with name " + senderName + " and subject " + subject);
        }
        wait.until(ExpectedConditions.visibilityOf(starButtons.get(0)));
        return starButtons.get(0).isDisplayed();
    }

    public void openLatestEmailBySenderAndSubject(String senderName, String subject) {
        String emailRowXPath = "//tr[.//span[contains(text(), '" + senderName + "')] and .//span[contains(text(), '" + subject + "')]]";
        List<WebElement> emailRows = driver.findElements(By.xpath(emailRowXPath));
        if (emailRows.isEmpty()) {
            throw new IllegalArgumentException("Could not find any emails from the sender with name " + senderName + " and subject " + subject + " in the current view");
        }
        wait.until(ExpectedConditions.elementToBeClickable(emailRows.get(0)));
        emailRows.get(0).click();
    }


    public boolean isLoginSuccessful() {
        try {
            return hamburgerMenu.isDisplayed();
        } catch (NoSuchElementException nsee) {
            LOG.warn("Hamburger menu on Gmail homepage is not observed", nsee);
            return false;
        }
    }

    public boolean isEmailSendSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailSuccessPopupText));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            LOG.warn("Email success popup is not observed", e);
            return false;
        }
    }

    public boolean isSocialTabEmpty() {
        try {
            return socialTabEmptyText.isDisplayed();
        } catch (NoSuchElementException nsee) {
            LOG.warn("Social tab is empty", nsee);
            return false;
        }
    }
}
