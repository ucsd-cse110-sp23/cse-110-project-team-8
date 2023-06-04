package cse110;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;


public class EmailInfoTest {
    String userId;
    String firstName;
    String lastName;
    String displayName;
    String emailAddress;
    String smtp;
    String tls;
    String password;
    EmailInfo testEi;

    @BeforeEach
    void setUp() {
        userId = "6471bcd5286e654cd761c8db";
        firstName = "Helen";
        lastName = "SayIt";
        displayName = "Hel";
        emailAddress = "example@gmail.com";
        smtp = "smtp.gmail.com";
        tls = "587";
        password = "password";

        testEi = new EmailInfo()
            .setUserId(userId)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setdisplayName(displayName)
            .setEmailAddress(emailAddress)
            .setSmtp(smtp)
            .setTls(tls)
            .setPassword(password);
    }

    @Test
    void testFromJson() {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty(EmailInfo.userIdKey, userId);
        jsonObj.addProperty(EmailInfo.firstNameKey, firstName);
        jsonObj.addProperty(EmailInfo.lastNameKey, lastName);
        jsonObj.addProperty(EmailInfo.displayNameKey, displayName);
        jsonObj.addProperty(EmailInfo.emailAddressKey, emailAddress);
        jsonObj.addProperty(EmailInfo.smtpKey, smtp);
        jsonObj.addProperty(EmailInfo.tlsKey, tls);
        jsonObj.addProperty(EmailInfo.passwordKey, password);

        EmailInfo ei = EmailInfo.fromJson(jsonObj);
        assertEquals(userId, ei.getUserId());
        assertEquals(firstName, ei.getFirstName());
        assertEquals(lastName, ei.getLastName());
        assertEquals(displayName, ei.getDisplayName());
        assertEquals(emailAddress, ei.getEmailAddress());
        assertEquals(smtp, ei.getSmtp());
        assertEquals(tls, ei.getTls());
        assertEquals(password, ei.getPassword());
    }

    @Test
    void testFromDocument() {
        Document doc = new Document()
            .append(EmailInfo.userIdKey, userId)
            .append(EmailInfo.firstNameKey, firstName)
            .append(EmailInfo.lastNameKey, lastName)
            .append(EmailInfo.displayNameKey, displayName)
            .append(EmailInfo.emailAddressKey, emailAddress)
            .append(EmailInfo.smtpKey, smtp)
            .append(EmailInfo.tlsKey, tls)
            .append(EmailInfo.passwordKey, password);

        EmailInfo ei = EmailInfo.fromDocument(doc);
        assertEquals(userId, ei.getUserId());
        assertEquals(firstName, ei.getFirstName());
        assertEquals(lastName, ei.getLastName());
        assertEquals(displayName, ei.getDisplayName());
        assertEquals(emailAddress, ei.getEmailAddress());
        assertEquals(smtp, ei.getSmtp());
        assertEquals(tls, ei.getTls());
        assertEquals(password, ei.getPassword());
    }


    @Test
    void testToJson() {
        JsonObject jsonObj = testEi.toJson();

        assertEquals(userId, jsonObj.get(EmailInfo.userIdKey).getAsString());
        assertEquals(firstName, jsonObj.get(EmailInfo.firstNameKey).getAsString());
        assertEquals(lastName, jsonObj.get(EmailInfo.lastNameKey).getAsString());
        assertEquals(displayName, jsonObj.get(EmailInfo.displayNameKey).getAsString());
        assertEquals(emailAddress, jsonObj.get(EmailInfo.emailAddressKey).getAsString());
        assertEquals(smtp, jsonObj.get(EmailInfo.smtpKey).getAsString());
        assertEquals(tls, jsonObj.get(EmailInfo.tlsKey).getAsString());
        assertEquals(password, jsonObj.get(EmailInfo.passwordKey).getAsString());
    }

    @Test
    void testToDocument() {
        Document doc = testEi.toDocument();

        assertEquals(userId, doc.get(EmailInfo.userIdKey).toString());
        assertEquals(firstName, doc.get(EmailInfo.firstNameKey));
        assertEquals(lastName, doc.get(EmailInfo.lastNameKey));
        assertEquals(displayName, doc.get(EmailInfo.displayNameKey));
        assertEquals(emailAddress, doc.get(EmailInfo.emailAddressKey));
        assertEquals(smtp, doc.get(EmailInfo.smtpKey));
        assertEquals(tls, doc.get(EmailInfo.tlsKey));
        assertEquals(password, doc.get(EmailInfo.passwordKey));
    }
}
