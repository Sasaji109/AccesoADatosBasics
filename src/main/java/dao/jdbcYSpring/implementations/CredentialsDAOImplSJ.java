package dao.jdbcYSpring.implementations;

import common.configuration.DBConnection;
import common.uitls.Constants;
import common.uitls.SQLQueries;
import dao.jdbcYSpring.CredentialsDAOSJ;
import domain.model.springJDBC.Credentials;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CredentialsDAOImplSJ implements CredentialsDAOSJ {
    private final DBConnection db;

    @Inject
    public CredentialsDAOImplSJ(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<ErrorC, List<Credentials>> getAllJDBC() {
        Either<ErrorC, List<Credentials>> either;

        List<Credentials> credentials = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_CREDENTIALS);

            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String userName = rs.getString("user_name");
                String password = rs.getString("password");

                Credentials credential = new Credentials(customerId,userName,password);
                credentials.add(credential);
            }
            either = Either.right(credentials);

        } catch (SQLException ex) {
            either = Either.left(new ErrorC(3, Constants.ERROR_ON_LOADING_CREDENTIALS, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Credentials> getJDBC(Credentials credentials) {
        Either<ErrorC, Credentials> either;

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CREDENTIAL_BY_ID)) {
            preparedStatement.setInt(1, credentials.getCustomerId());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String userName = rs.getString("user_name");
                String password = rs.getString("password");

                Credentials credential = new Credentials(customerId,userName,password);

                either = Either.right(credential);
            } else {
                either = Either.left(new ErrorC(3, Constants.CREDENTIAL_NOT_FOUND, LocalDate.now()));
            }
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }
}







