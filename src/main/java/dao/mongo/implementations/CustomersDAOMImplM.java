package dao.mongo.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import common.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.CustomersDAOM;
import domain.adapter.ObjectIdAdapter;
import domain.model.ErrorC;
import domain.model.mongo.CredentialsMongo;
import domain.model.mongo.CustomersMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;

public class CustomersDAOMImplM implements CustomersDAOM {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ObjectId.class, new ObjectIdAdapter())
            .create();

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
    public Either<ErrorC, List<CustomersMongo>> getAllWithoutOrders() {
        Either<ErrorC, List<CustomersMongo>> either;

        try {
            MongoCollection<Document> est = mongoDatabase.getCollection("customers");
            List<CustomersMongo> customers = new ArrayList<>();
            List<Document> documents = est.find().projection(fields(exclude("orders"))).into(new ArrayList<>());

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
            MongoCollection<Document> credentialsCollection = mongoDatabase.getCollection("credentials");

            CustomersMongo customersMongo = new CustomersMongo(customer.get_id(), customer.getFirst_name(), customer.getLast_name(), customer.getEmail(), customer.getPhone(), customer.getDate_of_birth(), customer.getOrders());
            Document document = Document.parse(gson.toJson(customersMongo));
            customersCollection.insertOne(document);

            ObjectId customerId = (ObjectId) document.get("_id");
            CredentialsMongo credentialsMongo = new CredentialsMongo(customerId,customer.getCredentialsMongo().getUser_name(), customer.getCredentialsMongo().getPassword());
            Document credentialDocument = Document.parse(gson.toJson(credentialsMongo));
            credentialsCollection.insertOne(credentialDocument);

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
            MongoCollection<Document> collectionCustomers = mongoDatabase.getCollection("customers");
            String customerJson = gson.toJson(customer);
            Document customerDocument = Document.parse(customerJson);
            Document filter = new Document("_id", customer.get_id());
            UpdateResult updateResult = collectionCustomers.replaceOne(filter, customerDocument);

            if (updateResult.getModifiedCount() > 0) {
                either = Either.right(1);
            } else {
                either = Either.left(new ErrorC(5, Constants.MONGO_ERROR, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> delete(CustomersMongo customer) {
        Either<ErrorC, Integer> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");
            MongoCollection<Document> credentialsCollection = mongoDatabase.getCollection("credentials");

            customersCollection.deleteOne(eq("_id", customer.get_id()));
            credentialsCollection.deleteOne(eq("_id", customer.get_id()));
            either = Either.right(1);
        }
        catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
