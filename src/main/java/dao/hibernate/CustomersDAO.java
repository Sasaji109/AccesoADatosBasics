package dao.hibernate;

import domain.model.ErrorC;
import domain.model.hibernate.CustomersH;
import io.vavr.control.Either;
import java.util.List;

public interface CustomersDAO {
    Either<ErrorC, List<CustomersH>> getAll();
    Either<ErrorC, CustomersH> get(int id);
    Either<ErrorC, Integer> add(CustomersH customersH);
    Either<ErrorC, Integer> update(CustomersH customersH);
    Either<ErrorC, Integer> delete(CustomersH customersH, boolean orders);
}
