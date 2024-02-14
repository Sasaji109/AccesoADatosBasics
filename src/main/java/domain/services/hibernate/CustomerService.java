package domain.services.hibernate;

import dao.hibernate.CustomersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.Customers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import java.util.List;

public class CustomerService {
    private final CustomersDAO dao;
    @Inject
    public CustomerService(CustomersDAO dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<Customers>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, Customers> get(int id) {
        return dao.get(id);
    }
    public Either<ErrorC, Integer> add(Customers customers) {
        return dao.add(customers);
    }
    public Either<ErrorC, Integer> update(Customers customers) {
        return dao.update(customers);
    }
    public Either<ErrorC, Integer> delete(Customers customers, boolean orders) {
        return dao.delete(customers, orders);
    }
}
