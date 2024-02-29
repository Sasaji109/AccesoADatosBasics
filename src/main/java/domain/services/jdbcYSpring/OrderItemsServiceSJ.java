package domain.services.jdbcYSpring;

import dao.jdbcYSpring.OrderItemsDAOSJ;
import domain.model.springJDBC.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import java.util.List;

public class OrderItemsServiceSJ {
    private final OrderItemsDAOSJ dao;
    @Inject
    public OrderItemsServiceSJ(OrderItemsDAOSJ dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<OrderItem>> getAllJDBC() {
        return dao.getAllJDBC();
    }
    public Either<ErrorC, OrderItem> getSpring(int id) {
        return dao.getSpring(id);
    }
    public Either<ErrorC, Integer> deleteJDBC(OrderItem orderItem) {
        return dao.deleteJDBC(orderItem);
    }
}
