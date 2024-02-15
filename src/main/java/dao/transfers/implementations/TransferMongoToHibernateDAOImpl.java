package dao.transfers.implementations;

import com.mongodb.client.MongoDatabase;
import common.Constants;
import common.configuration.JPAUtil;
import common.configuration.MongoDBConfig;
import dao.transfers.TransferMongoToHibernateDAO;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

public class TransferMongoToHibernateDAOImpl implements TransferMongoToHibernateDAO {

    private final JPAUtil jpaUtil;
    private final MongoDatabase mongoDatabase;
    private EntityManager em;

    @Inject
    public TransferMongoToHibernateDAOImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    public Either<ErrorC, Integer> transferMongoToHibernate() {
        Either<ErrorC, Integer> either = null;

        try {
        } catch (Exception e) {
            either = Either.left(new ErrorC(6, Constants.MAPPING_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
