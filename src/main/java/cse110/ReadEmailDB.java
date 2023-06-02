package cse110;


import com.mongodb.client.*;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;


public class ReadEmailDB extends DBAccess {
    public static boolean existsEmailInfo(String userId) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("email");
            MongoCollection<Document> userCollection = accountsDB.getCollection("info");

            // find one document with Filters.eq()
            Document emailInfoDoc = userCollection.find(eq(EmailInfo.userIdKey, userId)).first();

            if (emailInfoDoc == null) {
                return false;
            }

            return true;
        }
    }

    public static EmailInfo getEmailInfo(String userId) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase accountsDB = mongoClient.getDatabase("email");
            MongoCollection<Document> userCollection = accountsDB.getCollection("info");

            // find one document with Filters.eq()
            Document emailInfoDoc = userCollection.find(eq(EmailInfo.userIdKey, userId)).first();
            return EmailInfo.fromDocument(emailInfoDoc);
        }
    }
}


