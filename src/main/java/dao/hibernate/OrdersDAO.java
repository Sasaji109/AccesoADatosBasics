package dao.hibernate;

import domain.model.ErrorC;
import domain.model.hibernate.Order;
import io.vavr.control.Either;
import java.util.List;

public interface OrdersDAO {
    Either<ErrorC, List<Order>> getAll();
    Either<ErrorC, Order> get(int orderId);
    Either<ErrorC, Integer> add(Order order);
    Either<ErrorC, Integer> update(Order order);
    Either<ErrorC, Integer> delete(Order order);
}
