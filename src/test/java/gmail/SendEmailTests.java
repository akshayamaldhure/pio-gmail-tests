package gmail;

import constants.GmailTestGroups;
import helpers.GmailHelper;
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

        // switch to the Social tab and validate non-empty state
        gmailHomePage.switchToSocialTab();

        // get the initial count of emails in the inbox
        final int initialEmailCount = gmailHomePage.getEmailsCount(SENDER_NAME, SUBJECT);

        // compose and send the email and validate success
        GmailHelper.composeEmail(gmailHomePage, RECIPIENTS, SUBJECT, BODY);
        GmailHelper.setEmailCategory(gmailHomePage, "Social");
        gmailHomePage.clickSendEmailButton();
        Assert.assertTrue(gmailHomePage.isEmailSendSuccessful(), "Email send failed");

        // wait for the email to be received based on the total email count
        GmailHelper.waitUntilTheEmailIsReceived(gmailHomePage, initialEmailCount, SENDER_NAME, SUBJECT);

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
