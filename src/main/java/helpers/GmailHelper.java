package helpers;

import pages.gmail.HomePage;

import java.util.List;

public class GmailHelper {

    public void composeEmail(HomePage gmailHomePage, List<String> recipients, String subject, String body) {
        gmailHomePage.clickComposeEmail();
        gmailHomePage.enterRecipientEmailAddresses(recipients);
        gmailHomePage.enterSubject(subject);
        gmailHomePage.enterBody(body);
    }

    public void setEmailCategory(HomePage gmailHomePage, String category) {
        gmailHomePage.clickMoreOptionsMenu();
        gmailHomePage.clickOnLabelMenu();
        gmailHomePage.selectCategory(category);
    }
}
