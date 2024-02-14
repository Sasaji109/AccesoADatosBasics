package dao.mongo;

import domain.model.ErrorC;
import domain.model.mongo.CustomersMongo;
import io.vavr.control.Either;
import org.bson.types.ObjectId;
import java.util.List;

public interface CustomersDAOM {
    Either<ErrorC, List<CustomersMongo>> getAll();
    Either<ErrorC, List<CustomersMongo>> getAllWithoutOrders();
    Either<ErrorC, CustomersMongo> get(ObjectId id);
    Either<ErrorC, CustomersMongo> add(CustomersMongo customer);
    Either<ErrorC, Integer> update(CustomersMongo customer);
    Either<ErrorC, Integer> delete(CustomersMongo customer);
}
