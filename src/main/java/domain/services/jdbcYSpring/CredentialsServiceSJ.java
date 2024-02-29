package domain.services.jdbcYSpring;

import dao.jdbcYSpring.CredentialsDAOSJ;
import domain.model.ErrorC;
import domain.model.springJDBC.Credentials;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import java.util.List;

public class CredentialsServiceSJ {
    private final CredentialsDAOSJ dao;
    @Inject
    public CredentialsServiceSJ(CredentialsDAOSJ dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<Credentials>> getAllJDBC() {
        return dao.getAllJDBC();
    }
    public Either<ErrorC, Credentials> getJDBC(Credentials credentials) {
        return dao.getJDBC(credentials);
    }
}
