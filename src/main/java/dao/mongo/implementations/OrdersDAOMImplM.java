package dao.mongo.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import common.uitls.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.OrdersDAOM;
import domain.adapter.ObjectIdAdapter;
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
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new ObjectIdAdapter()).create();


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
            for (Document customerDocument : customersCollection.find()) {
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

            // Crear un documento de consulta para buscar un cliente por su "_id"
            Document query = new Document("_id", customerId);

            // Buscar el primer documento que coincida con la consulta en la colección de clientes
            Document customerDoc = customersCollection.find(query).first();

            // Verificar si se encontró un cliente con el ID proporcionado
            if (customerDoc != null) {

                // Obtener la lista de documentos de orders dentro del documento del customer
                List<Document> ordersDoc = (List<Document>) customerDoc.get("orders");

                // Convertir el objeto 'order' a formato JSON utilizando la biblioteca Gson
                String orderJson = gson.toJson(order);

                // Parsear el JSON del order a un nuevo documento de MongoDB
                Document newOrderDoc = Document.parse(orderJson);

                // Agregar la fecha del pedido al nuevo documento
                newOrderDoc.put("order_date", order.getOrder_date());

                // Agregar el nuevo documento de pedido a la lista de documentos de pedidos
                ordersDoc.add(newOrderDoc);

                // Actualizar el documento del cliente en la colección con la nueva lista de pedidos
                customersCollection.updateOne(query, new Document("$set", new Document("orders", ordersDoc)));

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

            // Realizar una actualización en la colección "customers"
            UpdateResult updateResult = customersCollection.updateOne(
                    // Especificar el criterio de búsqueda para identificar al cliente por su "_id" y la fecha del pedido
                    and(eq("_id", customerId), eq("orders.order_date", order.getOrder_date())),
                    // Definir la actualización utilizando el operador $set para modificar el campo "orders" del cliente
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

            // Realizar una actualización en la colección "customers"
            UpdateResult updateResult = customersCollection.updateOne(
                    // Especificar el criterio de búsqueda para identificar al cliente por su "_id"
                    eq("_id", customerId),
                    // Utilizar el operador $pull para eliminar un elemento de la lista "orders" que coincida con la fecha del pedido
                    pull("orders", eq("order_date", order.getOrder_date()))
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
