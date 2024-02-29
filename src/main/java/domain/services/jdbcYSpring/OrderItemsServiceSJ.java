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

    public Either<ErrorC, OrderItem> getJDBC(Integer id) {
        return dao.getJDBC(id);
    }

    public Either<ErrorC, List<OrderItem>> getAllSpring() {
        return dao.getAllSpring();
    }

    public Either<ErrorC, OrderItem> getSpring(Integer id) {
        return dao.getSpring(id);
    }

    public Either<ErrorC, Integer> deleteJDBC(OrderItem orderItem) {
        return dao.deleteJDBC(orderItem);
    }
}
