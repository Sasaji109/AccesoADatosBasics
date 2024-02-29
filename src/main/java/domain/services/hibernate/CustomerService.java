package domain.services.hibernate;

import dao.hibernate.CustomersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.CustomersH;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import java.util.List;

public class CustomerService {
    private final CustomersDAO dao;
    @Inject
    public CustomerService(CustomersDAO dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<CustomersH>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, CustomersH> get(int id) {
        return dao.get(id);
    }
    public Either<ErrorC, Integer> add(CustomersH customersH) {
        return dao.add(customersH);
    }
    public Either<ErrorC, Integer> update(CustomersH customersH) {
        return dao.update(customersH);
    }
    public Either<ErrorC, Integer> delete(CustomersH customersH, boolean orders) {
        return dao.delete(customersH, orders);
    }
}
