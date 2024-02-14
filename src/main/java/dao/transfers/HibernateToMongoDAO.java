package dao.transfers;

import domain.model.ErrorC;
import io.vavr.control.Either;

public interface HibernateToMongoDAO {
    Either<ErrorC, Integer> transferHibernateToMongo();
}
