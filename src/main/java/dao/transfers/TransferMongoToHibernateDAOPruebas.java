package dao.transfers;

import domain.model.ErrorC;
import io.vavr.control.Either;

public interface TransferMongoToHibernateDAOPruebas {
    Either<ErrorC, Integer> transferMongoToHibernate();
}
