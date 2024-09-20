package gmail;

import constants.GmailTestGroups;
import helpers.GmailHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SendEmailTests extends BaseTest {

    @Test(groups = {GmailTestGroups.GMAIL, GmailTestGroups.NEEDS_LOGIN})
    public void verifySendEmailToSelfUnderSocialCategoryIsSuccessful() {
        String fromAddress = GMAIL_ADDRESS;
        String toAddress = fromAddress;
        final List<String> RECIPIENTS = List.of(toAddress);
        final String SUBJECT = "Test Mail";
        final String BODY = "Test Email Body";

        SoftAssert sofly = new SoftAssert();

        // compose and send the email and validate success
        new GmailHelper().composeEmail(gmailHomePage, RECIPIENTS, SUBJECT, BODY);
        new GmailHelper().setEmailCategory(gmailHomePage, "Social");
        gmailHomePage.clickSendEmailButton();
        Assert.assertTrue(gmailHomePage.isEmailSendSuccessful(), "Email send failed");

        // switch to the Social tab and validate non-empty state
        gmailHomePage.switchToSocialTab();
        Assert.assertFalse(gmailHomePage.isSocialTabEmpty(), "Social tab is empty on Gmail homepage");

        // star the email and validate star success
        gmailHomePage.starLatestEmailBySenderAndSubject("me", SUBJECT);
        sofly.assertTrue(gmailHomePage.isLatestEmailBySenderAndSubjectStarred("me", SUBJECT), "Could not star the latest email on Gmail homepage");

        // open the latest email received and validate the email details
        gmailHomePage.openLatestEmailBySenderAndSubject("me", SUBJECT);
        sofly.assertTrue(mailPage.isStarred(), "The email does not appear to be starred on the email full view page");
        sofly.assertTrue(mailPage.hasSubject(SUBJECT), "Incorrect email subject on the email full view page");
        sofly.assertTrue(mailPage.hasBody(BODY), "Incorrect email body on the email full view page");

        sofly.assertAll();
    }
}
