package dao.transfers;

import domain.model.ErrorC;
import io.vavr.control.Either;

public interface TransferMongoToHibernateDAO {
    Either<ErrorC, Integer> transferMongoToHibernate();
}
