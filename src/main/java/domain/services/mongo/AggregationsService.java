package domain.services.mongo;

import dao.mongo.AggregationsDAO;
import domain.model.ErrorC;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

public class AggregationsService {
    private final AggregationsDAO dao;
    @Inject
    public AggregationsService(AggregationsDAO dao) {
        this.dao = dao;
    }

    public Either<ErrorC, String> aggregationA() {
        return dao.aggregationA();
    }

    public Either<ErrorC, String> aggregationB(ObjectId customerId) {
        return dao.aggregationB(customerId);
    }

    public Either<ErrorC, List<String>> aggregationC() {
        return dao.aggregationC();
    }

    public Either<ErrorC, List<String>> aggregationD() {
        return dao.aggregationD();
    }

    public Either<ErrorC, String> aggregationE() {
        return dao.aggregationE();
    }

    public Either<ErrorC, String> aggregationF() {
        return dao.aggregationF();
    }

    public Either<ErrorC, List<String>> aggregationG(ObjectId customerId) {
        return dao.aggregationG(customerId);
    }

    public Either<ErrorC, String> aggregationH() {
        return dao.aggregationH();
    }

    public Either<ErrorC, List<String>> aggregationI() {
        return dao.aggregationI();
    }

    public Either<ErrorC, List<String>> aggregationJ() {
        return dao.aggregationJ();
    }

    public Either<ErrorC, List<String>> aggregationK() {
        return dao.aggregationK();
    }

    public Either<ErrorC, String> aggregationL() {
        return dao.aggregationL();
    }

    public Either<ErrorC, String> aggregationM() {
        return dao.aggregationM();
    }

    public Either<ErrorC, List<String>> aggregationComplex1() {
        return dao.aggregationComplex1();
    }

    public Either<ErrorC, List<String>> aggregationComplex2() {
        return dao.aggregationComplex2();
    }

    public Either<ErrorC, List<String>> aggregationComplex3() {
        return dao.aggregationComplex3();
    }

    public Either<ErrorC, String> aggregationComplex4() {
        return dao.aggregationComplex4();
    }

    public Either<ErrorC, String> aggregationComplex5() {
        return dao.aggregationComplex5();
    }
}
