package domain.services.transferData;

import dao.transfers.TransferHibernateToMongoDAO;
import dao.transfers.TransferMongoToHibernateDAO;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class TransferServices {
    private final TransferMongoToHibernateDAO transferMongoToHibernate;
    private final TransferHibernateToMongoDAO transferHibernateToMongo;

    @Inject
    public TransferServices(TransferMongoToHibernateDAO transferMongoToHibernate, TransferHibernateToMongoDAO transferHibernateToMongo) {
        this.transferMongoToHibernate = transferMongoToHibernate;
        this.transferHibernateToMongo = transferHibernateToMongo;
    }

    public Either<ErrorC, Integer> transferMongoToHibernate() {
        return transferMongoToHibernate.transferMongoToHibernate();
    }

    public Either<ErrorC, Integer> transferHibernateToMongo() {
        return transferHibernateToMongo.transferHibernateToMongo();
    }
}
