package dao.mongo;

import domain.model.ErrorC;
import domain.model.mongo.CredentialsMongo;
import io.vavr.control.Either;
import java.util.List;

public interface CredentialsDAOM {
    Either<ErrorC, List<CredentialsMongo>> getAll();
    Either<ErrorC, CredentialsMongo> get(String password);
}
