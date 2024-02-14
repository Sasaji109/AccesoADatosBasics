package domain.services.mongo;

import dao.transfers.HibernateToMongoDAO;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class HibernateServiceM {

    private final HibernateToMongoDAO dao;
    @Inject
    public HibernateServiceM(HibernateToMongoDAO dao) {
        this.dao = dao;
    }
    public Either<ErrorC, Integer> transferDataSQL() {
        return dao.transferHibernateToMongo();
    }
}
