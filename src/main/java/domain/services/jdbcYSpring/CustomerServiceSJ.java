package domain.services.jdbcYSpring;

import dao.jdbcYSpring.CustomersDAOSJ;
import domain.model.springJDBC.Customer;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import java.util.List;
public class CustomerServiceSJ {
    private final CustomersDAOSJ dao;

    @Inject
    public CustomerServiceSJ(CustomersDAOSJ dao) {
        this.dao = dao;
    }

    public Either<ErrorC, List<Customer>> getAllJDBC() {
        return dao.getAllJDBC();
    }

    public Either<ErrorC, List<Customer>> getAllSpring() {
        return dao.getAllSpring();
    }

    public Either<ErrorC, Customer> getJDBC(Integer id) {
        return dao.getJDBC(id);
    }

    public Either<ErrorC, Customer> getSpring(Integer id) {
        return dao.getSpring(id);
    }

    public Either<ErrorC, Integer> addJDBC(Customer customer) {
        return dao.addJDBC(customer);
    }

    public Either<ErrorC, Integer> addSpring(Customer customer) {
        return dao.addSpring(customer);
    }

    public Either<ErrorC, Integer> updateJDBC(Customer customer) {
        return dao.updateJDBC(customer);
    }

    public Either<ErrorC, Integer> updateSpring(Customer customer) {
        return dao.updateSpring(customer);
    }

    public Either<ErrorC, Integer> deleteJDBC(Integer id, boolean orders) {
        return dao.deleteJDBC(id, orders);
    }

    public Either<ErrorC, Integer> deleteSpring(Integer id, boolean orders) {
        return dao.deleteSpring(id, orders);
    }
}

