package dao.jdbcYSpring;

import domain.model.springJDBC.OrderItem;
import io.vavr.control.Either;
import domain.model.ErrorC;
import java.util.List;

public interface OrderItemsDAOSJ {
    Either<ErrorC, List<OrderItem>> getAllJDBC();
    Either<ErrorC, OrderItem> getJDBC(Integer id);
    Either<ErrorC, List<OrderItem>> getAllSpring();
    Either<ErrorC, OrderItem> getSpring(Integer id);
    Either<ErrorC, Integer> deleteJDBC(OrderItem orderItem);
}
