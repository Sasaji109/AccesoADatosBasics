package domain.services.mongo;

import dao.mongo.CredentialsDAOM;
import domain.model.ErrorC;
import domain.model.mongo.CredentialsMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public class CredentialsServiceM {
    private final CredentialsDAOM dao;
    @Inject
    public CredentialsServiceM(CredentialsDAOM dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<CredentialsMongo>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, CredentialsMongo> get(String password) {
        return dao.get(password);
    }

    public Either<ErrorC, Document> add(CredentialsMongo credentials) {
        return dao.add(credentials);
    }

    public Either<ErrorC, Integer> delete(ObjectId id) {
        return dao.delete(id);
    }
}
