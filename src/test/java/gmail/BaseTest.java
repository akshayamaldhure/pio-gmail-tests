package gmail;

import constants.GmailTestGroups;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.gmail.HomePage;
import pages.gmail.LoginPage;
import pages.gmail.MailFullViewPage;

public class BaseTest {

    // below objects can/must be made ThreadLocal to support parallel tests
    WebDriver driver;
    LoginPage gmailLoginPage;
    HomePage gmailHomePage;
    MailFullViewPage mailPage;

    final String GMAIL_HOME_URL = "https://gmail.com";
    final String GMAIL_ADDRESS = System.getenv("GMAIL_ADDRESS");
    final String GMAIL_PASSWORD = System.getenv("GMAIL_PASSWORD");

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        driver = new ChromeDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void initializePages() {
        gmailLoginPage = new LoginPage(driver);
        gmailHomePage = new HomePage(driver);
        mailPage = new MailFullViewPage(driver);
    }

    @BeforeMethod(groups = {GmailTestGroups.GMAIL, GmailTestGroups.NEEDS_LOGIN})
    public void loginToGmail() {
        if (GMAIL_ADDRESS == null || GMAIL_PASSWORD == null) {
            throw new IllegalArgumentException("Invalid Gmail address or password. Please provide a valid Gmail username and password in the environment variables GMAIL_USERNAME and GMAIL_PASSWORD respectively to proceed.");
        }
        driver.get(GMAIL_HOME_URL);
        gmailLoginPage.enterEmail(GMAIL_ADDRESS);
        gmailLoginPage.enterPassword(GMAIL_PASSWORD);
        Assert.assertFalse(gmailLoginPage.isLoginFailure(), "Login failed due to incorrect password");
        Assert.assertTrue(gmailHomePage.isLoginSuccessful(), "Login failed as the Gmail homepage could not be found");
    }

    @AfterMethod(alwaysRun = true)
    public void teardownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
