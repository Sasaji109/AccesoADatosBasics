package dao.hibernate;

import domain.model.ErrorC;
import domain.model.hibernate.OrderH;
import io.vavr.control.Either;
import java.util.List;

public interface OrdersDAO {
    Either<ErrorC, List<OrderH>> getAll();
    Either<ErrorC, OrderH> get(int orderId);
    Either<ErrorC, Integer> add(OrderH orderH);
    Either<ErrorC, Integer> update(OrderH orderH);
    Either<ErrorC, Integer> delete(OrderH orderH);
}
