package dao.mongo;

import domain.model.ErrorC;
import io.vavr.control.Either;
import org.bson.types.ObjectId;
import java.util.List;

public interface AggregationsDAO {
    Either<ErrorC, String> aggregationA();
    Either<ErrorC, String> aggregationB(ObjectId customerId);
    Either<ErrorC, List<String>> aggregationC();
    Either<ErrorC, List<String>> aggregationD();
    Either<ErrorC, String> aggregationE();
    Either<ErrorC, String> aggregationF();
    Either<ErrorC, List<String>> aggregationG(ObjectId customerId);
    Either<ErrorC, String> aggregationH();
    Either<ErrorC, List<String>> aggregationI();
    Either<ErrorC, List<String>> aggregationJ();
    Either<ErrorC, List<String>> aggregationK();
    Either<ErrorC, String> aggregationL();
    Either<ErrorC, String> aggregationM();
    Either<ErrorC, List<String>> aggregationComplex1();
    Either<ErrorC, List<String>> aggregationComplex2();
    Either<ErrorC, List<String>> aggregationComplex3();
    Either<ErrorC, String> aggregationComplex4();
    Either<ErrorC, String> aggregationComplex5();
}
