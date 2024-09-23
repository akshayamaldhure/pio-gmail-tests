package gmail;

import constants.GmailTestGroups;
import utils.GmailActions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SendEmailTests extends GmailBaseTest {

    @Test(groups = {GmailTestGroups.GMAIL, GmailTestGroups.NEEDS_LOGIN})
    public void verifySendEmailToSelfUnderSocialCategoryIsSuccessful() {
        String fromAddress = GMAIL_ADDRESS;
        String toAddress = fromAddress;
        final List<String> RECIPIENTS = List.of(toAddress);
        final String SUBJECT = "Test Mail";
        final String BODY = "Test Email Body";
        final String SENDER_NAME = "me";

        SoftAssert sofly = new SoftAssert();

        // switch to the Social tab
        // while the email marked as Social arrives under the Social tab correctly, the email full view somehow shows the label as Inbox
        // so we are validating the label here by explicitly switching to the Social tab
        gmailHomePage.switchToSocialTab();

        // get the initial count of emails in the current view (Social tab in this case)
        final int initialEmailCount = gmailHomePage.getEmailsCount(SENDER_NAME, SUBJECT);

        // compose and send the email and validate success
        GmailActions.composeEmail(gmailHomePage, RECIPIENTS, SUBJECT, BODY);
        GmailActions.setEmailCategory(gmailHomePage, "Social");
        gmailHomePage.clickSendEmailButton();
        Assert.assertTrue(gmailHomePage.isEmailSendSuccessful(), "Email send failed");

        // wait for the email to be received (based on the total email count)
        GmailActions.waitUntilTheEmailIsReceived(gmailHomePage, initialEmailCount, SENDER_NAME, SUBJECT);

        // ensure the Social tab is not empty
        Assert.assertFalse(gmailHomePage.isSocialTabEmpty(), "Social tab is empty on Gmail homepage");

        // mark the email as starred and validate starring success
        gmailHomePage.starLatestEmailBySenderAndSubject(SENDER_NAME, SUBJECT);
        sofly.assertTrue(gmailHomePage.isLatestEmailBySenderAndSubjectStarred(SENDER_NAME, SUBJECT), "Could not star the latest email on Gmail homepage");

        // open the latest email received and validate the email details
        gmailHomePage.openLatestEmailBySenderAndSubject(SENDER_NAME, SUBJECT);
        sofly.assertTrue(mailPage.isStarred(), "The email does not appear to be starred on the email full view page");
        sofly.assertTrue(mailPage.hasSubject(SUBJECT), "Incorrect email subject on the email full view page");
        sofly.assertTrue(mailPage.hasBody(BODY), "Incorrect email body on the email full view page");

        sofly.assertAll();
    }
}
