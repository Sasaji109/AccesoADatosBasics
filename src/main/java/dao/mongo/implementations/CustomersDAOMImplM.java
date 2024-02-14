package dao.mongo.implementations;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import common.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.CustomersDAOM;
import domain.model.ErrorC;
import domain.model.mongo.CustomersMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CustomersDAOMImplM implements CustomersDAOM {

    private final MongoDatabase mongoDatabase;

    @Inject
    public CustomersDAOMImplM() {
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    @Override
    public Either<ErrorC, List<CustomersMongo>> getAll() {
        Either<ErrorC, List<CustomersMongo>> either;

        try {
            MongoCollection<Document> est = mongoDatabase.getCollection("customers");
            List<CustomersMongo> customers = new ArrayList<>();
            List<Document> documents = est.find().into(new ArrayList<>());

            for (Document supplier : documents) {
                customers.add(new Gson().fromJson(supplier.toJson(), CustomersMongo.class));
            }
            either = Either.right(customers);

        } catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, CustomersMongo> get(ObjectId id) {
        Either<ErrorC, CustomersMongo> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            Document query = new Document("_id", id);
            Document customerDocument = customersCollection.find(query).first();

            if (customerDocument != null) {
                CustomersMongo customer = new Gson().fromJson(customerDocument.toJson(), CustomersMongo.class);
                either = Either.right(customer);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }

        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, CustomersMongo> add(CustomersMongo customer) {
        Either<ErrorC, CustomersMongo> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");
            Document document = Document.parse(new Gson().toJson(customer));
            customersCollection.insertOne(document);
            either = Either.right(customer);
        }
        catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> update(CustomersMongo customer) {
        Either<ErrorC, Integer> either;

        try {
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
            MongoCollection<CustomersMongo> customersCollection = mongoDatabase.getCollection("customers", CustomersMongo.class).withCodecRegistry(pojoCodecRegistry);
            UpdateResult result = customersCollection.replaceOne(eq("_id", customer.get_id()), customer);

            either = Either.right((int) result.getModifiedCount());
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> delete(ObjectId id) {
        Either<ErrorC, Integer> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");
            customersCollection.deleteOne(eq("_id", id));
            either = Either.right(1);
        }
        catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
