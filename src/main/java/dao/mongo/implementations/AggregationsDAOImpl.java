package dao.mongo.implementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UnwindOptions;
import common.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.AggregationsDAO;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static java.util.Arrays.asList;

public class AggregationsDAOImpl implements AggregationsDAO {

    private final MongoDatabase mongoDatabase;

    @Inject
    public AggregationsDAOImpl() {
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    @Override
    public Either<ErrorC, String> aggregationA() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> menuItemsCollection = mongoDatabase.getCollection("menuitems");

            List<Document> document = menuItemsCollection.aggregate(asList(
                    sort(descending("price")),
                    limit(1),
                    project(fields(excludeId(), include("description")))
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                either = Either.right(document.get(0).get("description", String.class));
            } else {
                either = Either.left(new ErrorC(404, Constants.MENU_ITEM_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationB(ObjectId customerId) {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> document = customersCollection.aggregate(asList(
                    match(eq("_id", customerId)),
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                    project(fields(
                            include("first_name"),
                            computed("order", "$orders.order_date"),
                            computed("tableNumber", "$orders.table_id"))
                    )
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationC() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> documents = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                    unwind("$orders.order_items"),
                    group("$orders.order_date", sum("NrItems", 1)),
                    project(fields(include("_id", "NrItems")))))
            .into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationD() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> documents = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    unwind("$orders.order_items", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    lookup("menuitems", "orders.order_items.menu_item_id", "_id", "menu_item"),
                    match(eq("menu_item.name", "Steak")),
                    addFields(new Field<>("customerName", new Document("$concat", asList("$first_name", " ", "$last_name")))),
                    project(fields(include("customerName"), excludeId()))
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationE() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> document = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                    group(null, sum("totalItems", new Document("$sum", new Document("$size",
                                    new Document("$ifNull", asList("$orders.order_items", Collections.emptyList()))))),
                            sum("totalOrders", 1)
                    ),
                    project(fields(excludeId(),
                        include("order_id"),
                        computed("averageItemsPerOrder", new Document("$divide", asList("$totalItems", "$totalOrders")))))
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationF() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> document = customersCollection.aggregate(asList(
                    unwind("$orders"),
                    unwind("$orders.order_items"),
                    group("$orders.order_items.menu_item_id",
                            sum("totalQuantity", "$orders.order_items.quantity")),
                    sort(ascending("totalQuantity")),
                    limit(1),
                    project(Projections.fields(
                            excludeId(),
                            computed("menu_item_id", "$_id"),
                            include("totalQuantity")
                    ))
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationG(ObjectId customerId) {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> documents = customersCollection.aggregate(asList(
                    match(eq("_id", customerId)),
                    unwind("$orders"),
                    unwind("$orders.order_items"),
                    group("$orders.order_items.menu_item_id",
                            sum("totalQuantity", "$orders.order_items.quantity")
                    )
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationH() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> document = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group("$orders.table_id", sum("total_orders", 1)),
                    sort(descending("total_orders")),
                    limit(1)
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationI() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> documents = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group(new Document("customer_id", "$_id").append("table_id", "$orders.table_id"),
                            sum("total_orders", 1)
                    ),
                    sort(descending("total_orders")),
                    group("$_id.customer_id",
                            first("most_requested_table", "$_id.table_id"),
                            first("total_orders", "$total_orders")
                    )
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationJ() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> documents = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    unwind("$orders.order_items", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group(
                            eq("_id", new Document("customer_id", "$_id").append("menu_item_id", "$orders.order_items.menu_item_id")),
                            sum("quantity", "$orders.order_items.quantity")
                    ),

                    match(eq("quantity", 1)),
                    project(fields(
                            excludeId(),
                            computed("menu_item_id", "$_id.menu_item_id")
                    ))
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationK() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> documents = customersCollection.aggregate(asList(
                    unwind("$orders"),
                    unwind("$orders.order_items"),
                    lookup("menuitems", "orders.order_items.menu_item_id", "_id", "menu_item"),
                    unwind("$menu_item"),
                    group("$orders.order_date", sum("total_price_paid", new Document("$sum", new Document("$multiply",
                                            asList("$menu_item.price", "$orders.order_items.quantity"))))
                    )
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationL() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> document = customersCollection.aggregate(asList(
                    unwind("$orders"),
                    unwind("$orders.order_items"),
                    lookup("menuitems", "orders.order_items.menu_item_id", "_id", "menu_item"),
                    unwind("$menu_item"),
                    group("$_id",
                            sum("totalSpent", new Document("$multiply",
                                    asList("$orders.order_items.quantity", "$menu_item.price"))),
                            first("first_name", "$first_name"),
                            first("last_name", "$last_name")
                    ),
                    sort(descending("totalSpent")),
                    limit(1)
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationM() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

            List<Document> document = customersCollection.aggregate(asList(
                    unwind("$orders", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    unwind("$orders.order_items", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    lookup("menuitems", "orders.order_items.menu_item_id", "_id", "menu_item"),
                    unwind("$menu_item", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group(null,
                            sum("total_paid_amount", new Document("$sum", new Document("$multiply",
                                            asList("$orders.order_items.quantity", "$menu_item.price"))))
                    ),
                    project(fields(excludeId(), include("total_paid_amount")))
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationComplex1() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> complexCollection = mongoDatabase.getCollection("complexjson");

            List<Document> documents = complexCollection.aggregate(asList(
                    unwind("$jobs"),
                    unwind("$jobs.tasks"),
                    group("$_id", sum("totalHours", "$jobs.tasks.hours"))
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationComplex2() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> complexCollection = mongoDatabase.getCollection("complexjson");

            List<Document> documents = complexCollection.aggregate(asList(
                    unwind("$jobs", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    unwind("$jobs.tasks", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group("$jobs.tasks.task_id", avg("avgHoursPerTask", "$jobs.tasks.hours")),
                    sort(ascending("_id"))
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, List<String>> aggregationComplex3() {
        Either<ErrorC, List<String>> either;

        try {
            MongoCollection<Document> complexCollection = mongoDatabase.getCollection("complexjson");

            List<Document> documents = complexCollection.aggregate(asList(
                    unwind("$jobs", new UnwindOptions().preserveNullAndEmptyArrays(true)),
                    unwind("$jobs.tasks", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group("$address.city", sum("totalHours", "$jobs.tasks.hours")),
                    sort(descending("totalHours"))
            )).into(new ArrayList<>());

            List<String> results = documents.stream()
                    .map(Document::toJson)
                    .collect(Collectors.toList());

            if (!results.isEmpty()) {
                either = Either.right(results);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationComplex4() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> complexCollection = mongoDatabase.getCollection("complexjson");

            List<Document> document = complexCollection.aggregate(asList(
                    unwind("$jobs", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    unwind("$jobs.tasks", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group("$_id",
                            sum("totalHours", "$jobs.tasks.hours"),
                            first("personDetails", "$$CURRENT")
                    ),
                    sort(descending("totalHours")),
                    limit(1)
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, String> aggregationComplex5() {
        Either<ErrorC, String> either;

        try {
            MongoCollection<Document> complexCollection = mongoDatabase.getCollection("complexjson");

            List<Document> document = complexCollection.aggregate(asList(
                    unwind("$jobs", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    unwind("$jobs.tasks", new UnwindOptions().preserveNullAndEmptyArrays(false)),
                    group("$jobs.job_date", sum("totalHours", "$jobs.tasks.hours")),
                    sort(descending("totalHours")),
                    limit(1)
            )).into(new ArrayList<>());

            if (!document.isEmpty()) {
                String result = document.get(0).toJson();
                either = Either.right(result);
            } else {
                either = Either.left(new ErrorC(404, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }
}
