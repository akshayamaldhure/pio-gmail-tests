package gmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.gmail.HomePage;
import pages.gmail.LoginPage;
import pages.gmail.MailFullViewPage;

public class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    // below objects can/must be made ThreadLocal to support parallel tests
    WebDriver driver;
    LoginPage gmailLoginPage;
    HomePage gmailHomePage;
    MailFullViewPage mailPage;

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {
        LOG.info("Initialising ChromeDriver instance for Google Chrome browser");
        driver = new ChromeDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void initializePages() {
        LOG.info("Initialising the page objects");
        gmailLoginPage = new LoginPage(driver);
        gmailHomePage = new HomePage(driver);
        mailPage = new MailFullViewPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void teardownDriver() {
        if (driver != null) {
            LOG.info("Terminating the Selenium WebDriver session and closing the Chrome browser instance");
            driver.quit();
        }
    }
}
