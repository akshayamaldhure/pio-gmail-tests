package pages.gmail;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

public class MailFullViewPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(MailFullViewPage.class);

    public MailFullViewPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@data-tooltip='Starred' and @aria-checked='true']")
    private WebElement emailStarred;

    public boolean hasSubject(String subject) {
        try {
            return driver.findElement(By.xpath("//h2[contains(text(),'" + subject + "')]")).isDisplayed();
        } catch (NoSuchElementException nsee) {
            LOG.warn("Email subject {} not found", subject);
            return false;
        }
    }

    public boolean hasBody(String body) {
        try {
            return driver.findElement(By.xpath("//div[contains(text(),'" + body + "')]")).isDisplayed();
        } catch (NoSuchElementException nsee) {
            LOG.warn("Email body {} not found", body);
            return false;
        }
    }

    public boolean isStarred() {
        try {
            return emailStarred.isDisplayed();
        } catch (NoSuchElementException nsee) {
            LOG.warn("The email does not appear to be starred", nsee);
            return false;
        }
    }
}
