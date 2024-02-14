package dao.mongo.implementations;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.CredentialsDAOM;
import domain.model.ErrorC;
import domain.model.mongo.CredentialsMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CredentialsDAOMImplM implements CredentialsDAOM {

    private final MongoDatabase mongoDatabase;

    @Inject
    public CredentialsDAOMImplM() {
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    @Override
    public Either<ErrorC, List<CredentialsMongo>> getAll() {
        Either<ErrorC, List<CredentialsMongo>> either;

        try {
            MongoCollection<Document> est = mongoDatabase.getCollection("credentials");
            List<CredentialsMongo> credentials = new ArrayList<>();
            List<Document> documents = est.find().into(new ArrayList<>());

            for (Document supplier : documents) {
                credentials.add(new Gson().fromJson(supplier.toJson(), CredentialsMongo.class));
            }
            either = Either.right(credentials);

        } catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, CredentialsMongo> get(String password) {
        Either<ErrorC, CredentialsMongo> either;

        try {
            MongoCollection<Document> credentialsCollection = mongoDatabase.getCollection("credentials");
            Document credentialsDocument = credentialsCollection.find(eq("password", password)).first();

            if (credentialsDocument != null) {
                CredentialsMongo credentials = new Gson().fromJson(credentialsDocument.toJson(), CredentialsMongo.class);
                either = Either.right(credentials);
            } else {
                either = Either.left(new ErrorC(404, Constants.CREDENTIALS_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }


    @Override
    public Either<ErrorC, Document> add(CredentialsMongo credentials) {
        Either<ErrorC, Document> either;

        try {
            MongoCollection<Document> credentialsCollection = mongoDatabase.getCollection("credentials");
            Document document = Document.parse(new Gson().toJson(credentials));
            credentialsCollection.insertOne(document);

            either = Either.right(document);
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> delete(ObjectId id) {
        Either<ErrorC, Integer> either;

        try {
            MongoCollection<Document> credentialsCollection = mongoDatabase.getCollection("credentials");
            credentialsCollection.deleteOne(eq("_id", id));
            either = Either.right(1);
        }
        catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}







