package dao.jdbcYSpring.implementations;

import common.configuration.DBConnection;
import common.uitls.Constants;
import common.uitls.SQLQueries;
import dao.jdbcYSpring.CustomersDAOSJ;
import dao.jdbcYSpring.mappers.OrderMapper;
import domain.model.springJDBC.Credentials;
import domain.model.springJDBC.Customer;
import domain.model.springJDBC.Order;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDAOImplSJ implements CustomersDAOSJ {

    private final DBConnection db;

    @Inject
    public CustomersDAOImplSJ(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<ErrorC, List<Customer>> getAllJDBC() {
        Either<ErrorC, List<Customer>> either;

        List<Customer> customers = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_CUSTOMERS);

            while (rs.next()) {
                int idCustomers = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dateBirth = rs.getDate("date_of_birth").toLocalDate();

                Credentials credentials = null;

                Customer customer = new Customer(idCustomers, firstName, lastName, email, phone, dateBirth, credentials);
                customers.add(customer);
            }
            either = Either.right(customers);
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(3, Constants.ERROR_ON_LOADING_CUSTOMERS, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, List<Customer>> getAllSpring() {
        Either<ErrorC, List<Customer>> either;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            List<Customer> customers = jtm.query(SQLQueries.SELECT_CUSTOMERS, BeanPropertyRowMapper.newInstance(Customer.class));
            either = Either.right(customers);
        } catch (Exception ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Customer> getJDBC(Integer id) {
        Either<ErrorC, Customer> either;

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CUSTOMER_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int idCustomers = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                LocalDate dateBirth = rs.getDate("date_of_birth").toLocalDate();

                Credentials credentials = null;

                Customer customer2 = new Customer(idCustomers, firstName, lastName, email, phone, dateBirth, credentials);
                either = Either.right(customer2);
            } else {
                either = Either.left(new ErrorC(3, Constants.CUSTOMER_NOT_FOUND, LocalDate.now()));
            }
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Customer> getSpring(Integer id) {
        Either<ErrorC, Customer> either;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            Customer sup = jtm.queryForObject(SQLQueries.SELECT_CUSTOMER_BY_ID, BeanPropertyRowMapper.newInstance(Customer.class),id);
            either = Either.right(sup);
        } catch (Exception ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> addJDBC(Customer customer) {
        Either<ErrorC, Integer> either;
        int rowsAffected;

        try (Connection con = db.getConnection()) {
            try (PreparedStatement psCredential = con.prepareStatement(SQLQueries.INSERT_CREDENTIAL, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psCustomer = con.prepareStatement(SQLQueries.INSERT_CUSTOMER)) {

                con.setAutoCommit(false);

                psCredential.setString(1, customer.getFirstName());
                psCredential.setString(2, customer.getLastName());
                psCredential.executeUpdate();

                ResultSet rs = psCredential.getGeneratedKeys();

                if (rs.next()) {
                    int autoID = rs.getInt(1);

                    psCustomer.setInt(1, autoID);
                    psCustomer.setString(2, customer.getFirstName());
                    psCustomer.setString(3, customer.getLastName());
                    psCustomer.setString(4, customer.getEmail());
                    psCustomer.setString(5, String.valueOf(customer.getPhone()));
                    psCustomer.setDate(6, Date.valueOf(customer.getDateBirth()));

                    try {
                        rowsAffected = psCustomer.executeUpdate();
                        con.commit();
                        either = Either.right(rowsAffected);
                    } catch (SQLIntegrityConstraintViolationException e) {
                        con.rollback();
                        either = Either.left(new ErrorC(10, Constants.USERNAME_ALREADY_EXISTS, LocalDate.now()));
                    }
                } else {
                    either = Either.left(new ErrorC(3, Constants.ERROR_ON_CREATING_CREDENTIALS_FOR_THE_CUSTOMER, LocalDate.now()));
                }
            } catch (SQLException e) {
                con.rollback();
                either = Either.left(new ErrorC(3, Constants.ERROR_ON_ADDING_THE_CUSTOMER, LocalDate.now()));
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> addSpring(Customer customer) {
        Either<ErrorC, Integer> either;
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(db.getDataSource());
        TransactionStatus transactionStatus = null;

        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            transactionStatus = transactionManager.getTransaction(def);

            Map<String, Object> credentialParams = new HashMap<>();
            credentialParams.put("user_name", customer.getCredentials().getUsername());
            credentialParams.put("password", customer.getCredentials().getPassword());

            SimpleJdbcInsert jdbcInsertCredentials = new SimpleJdbcInsert(db.getDataSource()).withTableName("credentials")
                    .usingGeneratedKeyColumns("customer_id").usingColumns("user_name","password");

            int credentialId;
            try {
                credentialId = (int) jdbcInsertCredentials.executeAndReturnKey(credentialParams).longValue();
            } catch (DuplicateKeyException e) {
                transactionManager.rollback(transactionStatus);
                return Either.left(new ErrorC(10, Constants.USERNAME_ALREADY_EXISTS, LocalDate.now()));
            }
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("id", credentialId);
            customerParams.put("first_name", customer.getFirstName());
            customerParams.put("last_name", customer.getLastName());
            customerParams.put("email", customer.getEmail());
            customerParams.put("phone", customer.getPhone());
            customerParams.put("date_of_birth", Date.valueOf(customer.getDateBirth()));

            SimpleJdbcInsert jdbcInsertCustomers = new SimpleJdbcInsert(db.getDataSource()).withTableName("customers");

            int rowsAffected = jdbcInsertCustomers.execute(customerParams);
            transactionManager.commit(transactionStatus);

            if (rowsAffected > 0) {
                either = Either.right(rowsAffected);
            } else {
                either = Either.left(new ErrorC(3, Constants.ERROR_ON_ADDING_THE_CUSTOMER, LocalDate.now()));
            }
        } catch (Exception e) {
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> updateJDBC(Customer customer) {
        Either<ErrorC, Integer> either;
        int rowsAffected;

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_CUSTOMER)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, String.valueOf(customer.getPhone()));
            preparedStatement.setDate(5, Date.valueOf(customer.getDateBirth()));
            preparedStatement.setInt(6, customer.getId());

            rowsAffected = preparedStatement.executeUpdate();
            either = Either.right(rowsAffected);
        } catch (SQLException sqle) {
            either = Either.left(new ErrorC(8, Constants.SQL_ERROR + sqle.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> updateSpring(Customer customer) {
        Either<ErrorC, Integer> either;
        int rowsAffected;

        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            rowsAffected = jtm.update(SQLQueries.UPDATE_CUSTOMER, customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), Date.valueOf(customer.getDateBirth()), customer.getId());
            either = Either.right(rowsAffected);
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> deleteJDBC(Integer id, boolean orders) {
        Either<ErrorC, Integer> either;
        int rowsAffected;
        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement deleteOrderItemsStatement = con.prepareStatement(SQLQueries.DELETE_ORDER_ITEMS_BY_CUSTOMER)) {
                    deleteOrderItemsStatement.setInt(1, id);
                    if (orders) {
                        deleteOrderItemsStatement.executeUpdate();
                    }

                    try (PreparedStatement deleteOrdersStatement = con.prepareStatement(SQLQueries.DELETE_ORDERS_BY_CUSTOMER)) {
                        deleteOrdersStatement.setInt(1, id);
                        if (orders) {
                            deleteOrdersStatement.executeUpdate();
                        }

                        try (PreparedStatement deleteCustomerStatement = con.prepareStatement(SQLQueries.DELETE_CUSTOMER)) {
                            deleteCustomerStatement.setInt(1, id);
                            deleteCustomerStatement.executeUpdate();

                            try (PreparedStatement deleteCredentialsStatement = con.prepareStatement(SQLQueries.DELETE_CREDENTIAL)) {
                                deleteCredentialsStatement.setInt(1, id);
                                rowsAffected = deleteCredentialsStatement.executeUpdate();
                            }
                        }
                    }
                    con.commit();
                    con.setAutoCommit(true);
                    either = Either.right(rowsAffected);
                } catch (SQLException e) {
                    con.rollback();
                    con.setAutoCommit(true);
                    either = Either.left(new ErrorC(7, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
                }
            } catch (SQLException sqle) {
                either = Either.left(new ErrorC(7, Constants.SQL_ERROR + sqle.getMessage(), LocalDate.now()));
            }
        } catch (SQLException outerSqle) {
            either = Either.left(new ErrorC(7, Constants.SQL_ERROR + outerSqle.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> deleteSpring(Integer id, boolean orders) {
        Either<ErrorC, Integer> either;
        int rowsAffected;

        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(db.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());

            if (orders) {
                //List<Order> customerOrders = jtm.query(SQLQueries.SELECT_ORDERS_BY_CUSTOMER, new OrderMapper(), customer.getId());
                //backupOrdersToXml(customerOrders, customer);
            }

            if (orders) {
                jtm.update(SQLQueries.DELETE_ORDER_ITEMS_BY_CUSTOMER, id);
                jtm.update(SQLQueries.DELETE_ORDERS_BY_CUSTOMER, id);
            }

            jtm.update(SQLQueries.DELETE_CUSTOMER, id);
            rowsAffected = jtm.update(SQLQueries.DELETE_CREDENTIAL, id);

            transactionManager.commit(txStatus);
            either = Either.right(rowsAffected);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            either = Either.left(new ErrorC(7, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
