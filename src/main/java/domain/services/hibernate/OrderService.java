package domain.services.hibernate;

import dao.hibernate.OrdersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import java.util.List;

public class OrderService {
    private final OrdersDAO dao;
    @Inject
    public OrderService(OrdersDAO dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<Order>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, Order> get(int orderId) {
        return dao.get(orderId);
    }
    public Either<ErrorC, Integer> add(Order order) {
        return dao.add(order);
    }
    public Either<ErrorC, Integer> update(Order order) {
        return dao.update(order);
    }
    public Either<ErrorC, Integer> delete(Order order) {
        return dao.delete(order);
    }
}
