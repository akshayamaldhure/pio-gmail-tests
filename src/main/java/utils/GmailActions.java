package utils;

import org.awaitility.Awaitility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.gmail.HomePage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GmailActions {

    private static final Logger LOG = LoggerFactory.getLogger(GmailActions.class);

    public static void composeEmail(HomePage gmailHomePage, List<String> recipients, String subject, String body) {
        LOG.info("Composing an email with subject {} and body {}", subject, body);
        gmailHomePage.getComposeEmailButton().click();
        gmailHomePage.enterRecipientEmailAddresses(recipients);
        gmailHomePage.getSubjectInputText().sendKeys(subject);
        gmailHomePage.getEmailBodyInputText().sendKeys(body);
    }

    public static void setEmailCategory(HomePage gmailHomePage, String category) {
        LOG.info("Setting the category of the email to {}", category);
        gmailHomePage.getMoreOptionsMenu().click();
        gmailHomePage.getLabelMenu().click();
        gmailHomePage.selectCategory(category);
    }

    public static void waitUntilTheEmailIsReceived(HomePage gmailHomePage, int initialEmailsCount, String senderName, String subject) {
        Awaitility.with().pollInSameThread().pollInterval(2, TimeUnit.SECONDS).await().alias("Waiting until the total number of emails is increased by at least 1 to confirm email receipt").atMost(30, TimeUnit.SECONDS).until(() -> gmailHomePage.getEmailsCount(senderName, subject) - initialEmailsCount >= 1);
    }
}
