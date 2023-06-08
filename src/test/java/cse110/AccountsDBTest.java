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

import cse110.middleware.ResponseStrings;
import cse110.server.DBCredentials;
import cse110.server.DBRead;
import cse110.server.DBWrite;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;


public class AccountsDBTest {
    private static String newUsername;

    @BeforeEach
    void setUp() {
        newUsername = "testAddUser 2347198374982137598";
        DBCredentials.uri = "mongodb://localhost";
    }

    @AfterAll
    static void tearDown() {
        // Delete the newly created user in testAddNewUsername()
        try (MongoClient mongoClient = MongoClients.create(DBCredentials.uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("users");
            MongoCollection<Document> userCollection = accountsDB.getCollection("userData");

            // delete one document
            Bson filter = eq("username", newUsername);
            DeleteResult result = userCollection.deleteOne(filter);
            System.out.println(result);
        }
    }

    @Test
    void testVerifyCorrectLogin() {
        String username = "user1";
        String password = "pass";
        String res = DBRead.getUserData(username, password).get("response").getAsString();
        System.out.println(res);
        assertEquals(res, ResponseStrings.DATABASE_READ_SUCCESS);
    }

    @Test
    void testLoginIncorrectUsername() {
        String username = "kelfcJOOSJ Fi fjs oe8f  F83 jrof8e93w r";
        String password = "";
        String res = DBRead.getUserData(username, password).get("response").getAsString();
        System.out.println(res);
        assertEquals(res, ResponseStrings.USERNAME_ERROR);
    }

    @Test
    void testLoginIncorrectPassword() {
        String username = "user1";
        String password = "password321";
        String res = DBRead.getUserData(username, password).get("response").getAsString();
        System.out.println(res);
        assertEquals(res, ResponseStrings.PASSWORD_ERROR);
    }

    @Test
    void testAddNewUsername() {
        String password = "pw";
        Document userData = new Document()
            .append("username", newUsername)
            .append("password", password);
        String res = DBWrite.setUserData(userData).get("response").getAsString();
        System.out.println(res);
        assertEquals(res, ResponseStrings.DATABASE_WRITE_SUCCESS);
    }
}
