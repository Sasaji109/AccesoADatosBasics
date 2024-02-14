package dao.hibernate;

import domain.model.ErrorC;
import domain.model.hibernate.Customers;
import io.vavr.control.Either;
import java.util.List;

public interface CustomersDAO {
    Either<ErrorC, List<Customers>> getAll();
    Either<ErrorC, Customers> get(int id);
    Either<ErrorC, Integer> add(Customers customers);
    Either<ErrorC, Integer> update(Customers customers);
    Either<ErrorC, Integer> delete(Customers customers, boolean orders);
}
