package domain.services.jdbcYSpring;

import dao.jdbcYSpring.OrdersDAOSJ;
import domain.model.springJDBC.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import java.util.List;

public class OrderServiceSJ {
    private final OrdersDAOSJ dao;

    @Inject
    public OrderServiceSJ(OrdersDAOSJ dao) {
        this.dao = dao;
    }

    public Either<ErrorC, List<Order>> getAllSpring() {
        return dao.getAllSpring();
    }

    public Either<ErrorC, Order> getJDBC(Order order) {
        return dao.getJDBC(order);
    }

    public Either<ErrorC, Order> getSpring(int orderId) {
        return dao.getSpring(orderId);
    }

    public Either<ErrorC, Integer> addJDBC(Order order) {
        return dao.addJDBC(order);
    }

    public Either<ErrorC, Integer> updateJDBC(Order order) {
        return dao.updateJDBC(order);
    }

    public Either<ErrorC, Integer> deleteJDBC(Order order) {
        return dao.deleteJDBC(order);
    }
}

