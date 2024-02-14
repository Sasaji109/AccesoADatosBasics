package domain.services.mongo;

import dao.mongo.OrdersDAOM;
import domain.model.ErrorC;
import domain.model.mongo.OrderMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;

public class OrderServiceM {

    private final OrdersDAOM dao;

    @Inject
    public OrderServiceM(OrdersDAOM dao) {
        this.dao = dao;
    }

    public Either<ErrorC, List<OrderMongo>> getAll() {
        return dao.getAll();
    }

    public Either<ErrorC, Integer> add(OrderMongo order, ObjectId customerId) {
        return dao.add(order,customerId);
    }
    public Either<ErrorC, Integer> update(OrderMongo order, ObjectId customerId) {
        return dao.update(order,customerId);
    }
    public Either<ErrorC, Integer> delete(OrderMongo order, ObjectId customerId) {
        return dao.delete(order,customerId);
    }
}
