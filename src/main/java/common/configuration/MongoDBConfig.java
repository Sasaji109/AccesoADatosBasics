package common.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import common.Constants;

public class MongoDBConfig {

    public static MongoClient createMongoClient() {
        String connectionString = Constants.DATABASE_HOST + Constants.DATABASE_PORT;
        return MongoClients.create(connectionString);
    }

    public static MongoDatabase getMongoDatabase() {
        MongoClient mongoClient = createMongoClient();
        return mongoClient.getDatabase(Constants.DATABASE_NAME);
    }

    public static void closeMongoClient(MongoClient mongoClient) {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
