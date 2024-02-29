package dao.jdbcYSpring;

import domain.model.springJDBC.Customer;
import io.vavr.control.Either;
import domain.model.ErrorC;
import java.util.List;

public interface CustomersDAOSJ {
    Either<ErrorC, List<Customer>> getAllJDBC();
    Either<ErrorC, List<Customer>> getAllSpring();
    Either<ErrorC, Customer> getJDBC(Integer id);
    Either<ErrorC, Customer> getSpring(Integer id);
    Either<ErrorC, Integer> addJDBC(Customer customer);
    Either<ErrorC, Integer> addSpring(Customer customer);
    Either<ErrorC, Integer> updateJDBC(Customer customer);
    Either<ErrorC, Integer> updateSpring(Customer customer);
    Either<ErrorC, Integer> deleteJDBC(Integer id, boolean orders);
    Either<ErrorC, Integer> deleteSpring(Integer id, boolean orders);
}

