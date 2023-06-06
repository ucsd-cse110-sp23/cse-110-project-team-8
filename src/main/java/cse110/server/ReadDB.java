package cse110.server;


import com.mongodb.client.*;

import cse110.middleware.ResponseStrings;

import org.bson.Document;

import static com.mongodb.client.model.Filters.*;


public class ReadDB extends DBAccess {

    public static String checkLogin(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("accounts");
            MongoCollection<Document> userCollection = accountsDB.getCollection("loginInfo");

            // find one document with Filters.eq()
            Document user = userCollection.find(eq("username", username)).first();

            if (user == null) {
                // This username does not exist
                return ResponseStrings.USERNAME_ERROR;
            }

            if (!user.get("password").equals(password)) {
                // Password does not match
                return ResponseStrings.PASSWORD_ERROR;
            }
            return ResponseStrings.LOGIN_SUCCESS;
        }
    }
}


