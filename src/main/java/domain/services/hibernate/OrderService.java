package domain.services.hibernate;

import dao.hibernate.OrdersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.OrderH;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import java.util.List;

public class OrderService {
    private final OrdersDAO dao;
    @Inject
    public OrderService(OrdersDAO dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<OrderH>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, OrderH> get(int orderId) {
        return dao.get(orderId);
    }
    public Either<ErrorC, Integer> add(OrderH orderH) {
        return dao.add(orderH);
    }
    public Either<ErrorC, Integer> update(OrderH orderH) {
        return dao.update(orderH);
    }
    public Either<ErrorC, Integer> delete(OrderH orderH) {
        return dao.delete(orderH);
    }
}
