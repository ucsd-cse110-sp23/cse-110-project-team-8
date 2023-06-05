package cse110.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import cse110.middleware.EmailInfo;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;


public class DeleteEmailDB extends DBAccess {

    public static void deleteEmailInfo(String userId) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase emailDB = mongoClient.getDatabase("email");
            MongoCollection<Document> infoCollection = emailDB.getCollection("info");

            // delete one document
            Bson filter = eq(EmailInfo.userIdKey, userId);
            DeleteResult result = infoCollection.deleteOne(filter);
            System.out.println(result);
        }
    }
}

