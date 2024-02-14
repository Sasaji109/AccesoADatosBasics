package dao.mongo.implementations;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import common.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.OrdersDAOM;
import domain.model.ErrorC;
import domain.model.mongo.CustomersMongo;
import domain.model.mongo.OrderMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class OrdersDAOMImplM implements OrdersDAOM {

    private final MongoDatabase mongoDatabase;

    @Inject
    public OrdersDAOMImplM() {
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    @Override
    public Either<ErrorC, List<OrderMongo>> getAll() {
        Either<ErrorC, List<OrderMongo>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");
            List<OrderMongo> allOrders = new ArrayList<>();
            List<Document> customerDocuments = customersCollection.find().into(new ArrayList<>());

            for (Document customerDocument : customerDocuments) {
                CustomersMongo customer = new Gson().fromJson(customerDocument.toJson(), CustomersMongo.class);

                if (customer.getOrders() != null) {
                    allOrders.addAll(customer.getOrders());
                }
            }

            either = Either.right(allOrders);
        } catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> add(OrderMongo order, ObjectId customerId) {
        Either<ErrorC, Integer> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            UpdateResult updateResult = customersCollection.updateOne(eq("_id", customerId),
                    addToSet("orders", Document.parse(new Gson().toJson(order))));

            if (updateResult.getModifiedCount() > 0) {
                either = Either.right(1);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> update(OrderMongo order, ObjectId customerId) {
        Either<ErrorC, Integer> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            UpdateResult updateResult = customersCollection.updateOne(
                    and(eq("_id", customerId), eq("orders.order_date", order.getOrder_date())),
                    set("orders.$", Document.parse(new Gson().toJson(order)))
            );

            if (updateResult.getModifiedCount() > 0) {
                either = Either.right(1);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }

        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> delete(OrderMongo order, ObjectId customerId) {
        Either<ErrorC, Integer> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            UpdateResult updateResult = customersCollection.updateOne(
                    eq("_id", customerId), pull("orders", eq("order_date", order.getOrder_date()))
            );
            if (updateResult.getModifiedCount() > 0) {
                either = Either.right(1);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }

        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }
}
