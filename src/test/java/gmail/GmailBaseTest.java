package gmail;

import constants.GmailTestGroups;
import utils.TOTPUtil;
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
        Assert.assertFalse(gmailLoginPage.isLoginFailedDueToWrongPassword(), "Login failed due to incorrect password");
        String totp = TOTPUtil.getTOTP(GMAIL_SECRET);
        if (gmailLoginPage.isTotpTextInputVisible()) { // handles the case of "don't ask again on this device"
            gmailLoginPage.enterTotp(totp, true);
            Assert.assertFalse(gmailLoginPage.isLoginFailedDueToWrongTOTP(), "Login failed due to incorrect TOTP");
        }
        Assert.assertTrue(gmailHomePage.isLoginSuccessful(), "Login failed as the Gmail homepage could not be seen");
    }
}
