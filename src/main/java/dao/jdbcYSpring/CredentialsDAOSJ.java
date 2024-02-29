package dao.jdbcYSpring;

import domain.model.ErrorC;
import domain.model.springJDBC.Credentials;
import io.vavr.control.Either;
import java.util.List;

public interface CredentialsDAOSJ {
    Either<ErrorC, List<Credentials>> getAllJDBC();
    Either<ErrorC, Credentials> getJDBC(Credentials credentials);
}
