package dao.transfers;

import domain.model.ErrorC;
import io.vavr.control.Either;

public interface TransferHibernateToMongoDAO {
    Either<ErrorC, Integer> transferHibernateToMongo();
}
