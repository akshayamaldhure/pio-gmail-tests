package gmail;

import constants.GmailTestGroups;
import helpers.TOTPHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class GmailBaseTest extends BaseTest {

    final String GMAIL_HOME_URL = "https://gmail.com";
    final String GMAIL_ADDRESS = System.getenv("GMAIL_ADDRESS");
    final String GMAIL_PASSWORD = System.getenv("GMAIL_PASSWORD");
    final String GMAIL_SECRET = System.getenv("GMAIL_SECRET");

    @BeforeMethod(groups = {GmailTestGroups.GMAIL, GmailTestGroups.NEEDS_LOGIN})
    public void loginToGmail() {
        if (GMAIL_ADDRESS == null || GMAIL_PASSWORD == null || GMAIL_SECRET == null) {
            throw new IllegalArgumentException("Invalid/empty Gmail address and/or password and/or secret. Please provide valid Gmail credentials through the environment variables GMAIL_USERNAME, GMAIL_PASSWORD, GMAIL_SECRET respectively to proceed.");
        }
        driver.get(GMAIL_HOME_URL);
        gmailLoginPage.enterEmail(GMAIL_ADDRESS);
        gmailLoginPage.enterPassword(GMAIL_PASSWORD);
        Assert.assertFalse(gmailLoginPage.isLoginFailed(), "Login failed due to incorrect password");
        String totp = TOTPHelper.getTOTP(GMAIL_SECRET);
        if (gmailLoginPage.isTotpTextInputVisible()) {
            gmailLoginPage.enterTotp(totp, true);
        }
        Assert.assertTrue(gmailHomePage.isLoginSuccessful(), "Login failed as the Gmail homepage could not be found");
    }
}
