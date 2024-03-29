package cse110.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cse110.middleware.EmailInfo;

import org.bson.Document;


/**
 * Adds email info into mongoDB.
 */
public class CreateEmailDB extends DBCredentials {
    static final String EMAIL_INFO_SUCCESS = "Email info upload successful.";
    public static String addEmailInfo(EmailInfo ei) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase emailDB = mongoClient.getDatabase("email");
            MongoCollection<Document> infoCollection = emailDB.getCollection("info");

            // Add email info as a Document
            Document doc = ei.toDocument();
            infoCollection.insertOne(doc);
            return EMAIL_INFO_SUCCESS;
        }
    }
}

