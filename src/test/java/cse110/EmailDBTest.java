package cse110;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;


public class EmailDBTest {
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
        userId = "user1";
        firstName = "Helen";
        lastName = "SayIt";
        displayName = "Hel";
        emailAddress = "example@gmail.com";
        smtp = "smtp.gmail.com";
        tls = "587";
        password = "password123";

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
    void testExistsEmailInfo() {
        assertTrue(ReadEmailDB.existsEmailInfo(userId));
    }

    @Test
    void testGetEmailInfo() {
        EmailInfo ei = ReadEmailDB.getEmailInfo(userId);
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
    void testCreateAndDelete() {
        userId = "64768c434debd4569ceb5f55";
        testEi.setUserId(userId);
        System.out.println("testEI::"+testEi.getDisplayName());
        CreateEmailDB.addEmailInfo(testEi);
        assertTrue(ReadEmailDB.existsEmailInfo(userId));
        // Now Delete
        DeleteEmailDB.deleteEmailInfo(userId);
        assertFalse(ReadEmailDB.existsEmailInfo(userId));
    }
}
