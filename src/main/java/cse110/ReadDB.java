package cse110;


import com.mongodb.client.*;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;


public class ReadDB extends DBAccess {
    static String LOGIN_SUCCESS = "Success: Username and password match";
    static String USERNAME_ERROR = "Error: This username does not exist.";
    static String PASSWORD_ERROR = "Error: Password does not match.";

    public static String checkLogin(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("accounts");
            MongoCollection<Document> userCollection = accountsDB.getCollection("loginInfo");

            // find one document with Filters.eq()
            Document user = userCollection.find(eq("username", username)).first();

            if (user == null) {
                // This username does not exist
                return USERNAME_ERROR;
            }

            if (!user.get("password").equals(password)) {
                // Password does not match
                return PASSWORD_ERROR;
            }
            return LOGIN_SUCCESS;
        }
    }
}


