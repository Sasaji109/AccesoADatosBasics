package dao.mongo;

import domain.model.ErrorC;
import domain.model.mongo.OrderMongo;
import io.vavr.control.Either;
import org.bson.types.ObjectId;
import java.util.List;

public interface OrdersDAOM {
    Either<ErrorC, List<OrderMongo>> getAll();
    Either<ErrorC, Integer> add(OrderMongo order, ObjectId customerId);
    Either<ErrorC, Integer> update(OrderMongo order, ObjectId customerId);
    Either<ErrorC, Integer> delete(OrderMongo order, ObjectId customerId);
}
