package domain.services.mongo;

import dao.mongo.CustomersDAOM;
import domain.model.ErrorC;
import domain.model.mongo.CustomersMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;

public class CustomerServiceM {
    private final CustomersDAOM dao;
    @Inject
    public CustomerServiceM(CustomersDAOM dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<CustomersMongo>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, CustomersMongo> get(ObjectId id) {
        return dao.get(id);
    }
    public Either<ErrorC, CustomersMongo> add(CustomersMongo customers) {
        return dao.add(customers);
    }
    public Either<ErrorC, Integer> update(CustomersMongo customers) {
        return dao.update(customers);
    }
    public Either<ErrorC, Integer> delete(ObjectId id) {
        return dao.delete(id);
    }
}
