package dao.mongo;

import domain.model.ErrorC;
import domain.model.mongo.CredentialsMongo;
import io.vavr.control.Either;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public interface CredentialsDAOM {
    Either<ErrorC, List<CredentialsMongo>> getAll();
    Either<ErrorC, CredentialsMongo> get(String password);
    Either<ErrorC, Document> add(CredentialsMongo credentials);
    Either<ErrorC, Integer> delete(ObjectId id);
}
