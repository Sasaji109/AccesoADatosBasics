package dao.jdbcYSpring;

import domain.model.springJDBC.OrderItem;
import io.vavr.control.Either;
import domain.model.ErrorC;
import java.util.List;

public interface OrderItemsDAOSJ {
    Either<ErrorC, List<OrderItem>> getAllJDBC();
    Either<ErrorC, OrderItem> getSpring(int id);
    Either<ErrorC, Integer> deleteJDBC(OrderItem orderItem);
}
