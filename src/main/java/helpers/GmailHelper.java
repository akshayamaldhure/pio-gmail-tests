package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.gmail.HomePage;

import java.util.List;

public class GmailHelper {

    private static final Logger LOG = LoggerFactory.getLogger(GmailHelper.class);

    public void composeEmail(HomePage gmailHomePage, List<String> recipients, String subject, String body) {
        LOG.info("Composing an email with subject {} and body {}", subject, body);
        gmailHomePage.clickComposeEmail();
        gmailHomePage.enterRecipientEmailAddresses(recipients);
        gmailHomePage.enterSubject(subject);
        gmailHomePage.enterBody(body);
    }

    public void setEmailCategory(HomePage gmailHomePage, String category) {
        LOG.info("Setting the category of the email to {}", category);
        gmailHomePage.clickMoreOptionsMenu();
        gmailHomePage.clickOnLabelMenu();
        gmailHomePage.selectCategory(category);
    }
}
