package cse110;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;


public class AccountsDBTest {
    private static String newUsername;

    @BeforeEach
    void setUp() {
        newUsername = "testAddUser 2347198374982137598";
    }

    @AfterAll
    static void tearDown() {
        // Delete the newly created user in testAddNewUsername()
        try (MongoClient mongoClient = MongoClients.create(CreateDB.uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("accounts");
            MongoCollection<Document> userCollection = accountsDB.getCollection("loginInfo");

            // delete one document
            Bson filter = eq("username", newUsername);
            DeleteResult result = userCollection.deleteOne(filter);
            System.out.println(result);
        }
    }

    @Test
    void testVerifyCorrectLogin() {
        String username = "user1";
        String password = "password123";
        String res = ReadDB.checkLogin(username, password);
        System.out.println(res);
        assertEquals(res, ReadDB.LOGIN_SUCCESS);
    }

    @Test
    void testLoginIncorrectUsername() {
        String username = "kelfcJOOSJ Fi fjs oe8f  F83 jrof8e93w r";
        String password = "";
        String res = ReadDB.checkLogin(username, password);
        System.out.println(res);
        assertEquals(res, ReadDB.USERNAME_ERROR);
    }

    @Test
    void testLoginIncorrectPassword() {
        String username = "user1";
        String password = "password321";
        String res = ReadDB.checkLogin(username, password);
        System.out.println(res);
        assertEquals(res, ReadDB.PASSWORD_ERROR);
    }

    @Test
    void testAddExistingUsername() {
        String username = "user1";
        String password = "";
        String res = CreateDB.addUser(username, password);
        System.out.println(res);
        assertEquals(res, CreateDB.USERNAME_TAKEN);
    }

    @Test
    void testAddNewUsername() {
        String password = "pw";
        String res = CreateDB.addUser(newUsername, password);
        System.out.println(res);
        assertEquals(res, CreateDB.ADDED_USER);
    }
}
