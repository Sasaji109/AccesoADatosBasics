package dao.jdbcYSpring;

import domain.model.springJDBC.Order;
import io.vavr.control.Either;
import domain.model.ErrorC;
import java.util.List;

public interface OrdersDAOSJ {
    Either<ErrorC, List<Order>> getAllJDBC();
    Either<ErrorC, List<Order>> getAllSpring();
    Either<ErrorC, Order> getJDBC(Order order);
    Either<ErrorC, Order> getSpring(int orderId);
    Either<ErrorC, Integer> addJDBC(Order order);
    Either<ErrorC, Integer> updateJDBC(Order order);
    Either<ErrorC, Integer> deleteJDBC(Order order);
}
