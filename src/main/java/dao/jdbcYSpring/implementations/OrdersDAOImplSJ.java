package dao.jdbcYSpring.implementations;

import common.configuration.DBConnection;
import common.uitls.Constants;
import common.uitls.SQLQueries;
import dao.jdbcYSpring.OrdersDAOSJ;
import dao.jdbcYSpring.mappers.OrderMapper;
import domain.model.springJDBC.Order;
import domain.model.springJDBC.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImplSJ implements OrdersDAOSJ {

    private final DBConnection db;

    @Inject
    public OrdersDAOImplSJ(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<ErrorC, List<Order>> getAllJDBC() {
        Either<ErrorC, List<Order>> either;

        List<Order> orders = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ORDERS);

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();
                int customerId = rs.getInt("customer_id");
                int tableId = rs.getInt("table_id");
                List<OrderItem> orderItemList = null;

                Order order = new Order(orderId, orderDate, customerId, tableId, orderItemList);
                orders.add(order);
            }
            either = Either.right(orders);
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(3, Constants.ERROR_ON_LOADING_ORDERS, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, List<Order>> getAllSpring() {
        Either<ErrorC, List<Order>> either;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            List<Order> orders = jtm.query(SQLQueries.SELECT_ORDERS, new OrderMapper());
            either = Either.right(orders);
        } catch (Exception ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Order> getJDBC(Order order) {
        Either<ErrorC, Order> either;

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ORDER_BY_ID)) {
            preparedStatement.setInt(1, order.getOrderId());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();
                int customerId = rs.getInt("customer_id");
                int tableId = rs.getInt("table_id");
                List<OrderItem> orderItemList = null;

                Order foundOrder = new Order(orderId, orderDate, customerId, tableId, orderItemList);
                either = Either.right(foundOrder);
            } else {
                either = Either.left(new ErrorC(3, Constants.ORDER_NOT_FOUND, LocalDate.now()));
            }
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Order> getSpring(int orderId) {
        Either<ErrorC, Order> either;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            Order order = jtm.queryForObject(SQLQueries.SELECT_ORDER_BY_ID, new Object[]{orderId}, new OrderMapper());
            either = Either.right(order);
        } catch (Exception ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> addJDBC(Order order) {
        Either<ErrorC, Integer> either;

        List<Order> orderList = getAllSpring().get();
        int maxOrderId = orderList.stream().map(Order::getOrderId).max(Integer::compareTo).orElse(0);
        int newOrderId = maxOrderId + 1;
        order.setOrderId(newOrderId);
        order.setOrderDate(LocalDateTime.now());

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setInt(3, order.getCustomerId());
            preparedStatement.setInt(4, order.getTableId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedOrderId = generatedKeys.getInt(1);
                        order.setOrderId(generatedOrderId);

                        for (OrderItem orderItem : order.getOrderItemList()) {
                            orderItem.setOrderId(generatedOrderId);
                            try (PreparedStatement orderItemStatement = con.prepareStatement(SQLQueries.INSERT_ORDERITEM)) {
                                orderItemStatement.setInt(1, orderItem.getId());
                                orderItemStatement.setInt(2, orderItem.getOrderId());
                                orderItemStatement.setInt(3, orderItem.getMenuItem().getId());
                                orderItemStatement.setInt(4, orderItem.getQuantity());

                                int orderItemRowsAffected = orderItemStatement.executeUpdate();

                                if (orderItemRowsAffected <= 0) {
                                    either = Either.left(new ErrorC(3, Constants.ERROR_ON_ADDING_THE_ORDER_ITEM, LocalDate.now()));
                                    return either;
                                }
                            }
                        }
                    }
                }

                either = Either.right(rowsAffected);
            } else {
                either = Either.left(new ErrorC(3, Constants.ERROR_ON_ADDING_THE_ORDER, LocalDate.now()));
            }
        } catch (SQLException e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } catch (Exception e) {
            either = Either.left(new ErrorC(6, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> updateJDBC(Order order) {
        Either<ErrorC, Integer> either;
        int rowsAffected;

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_ORDER)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getTableId());
            preparedStatement.setInt(4, order.getOrderId());

            rowsAffected = preparedStatement.executeUpdate();
            either = Either.right(rowsAffected);
        } catch (SQLException sqle) {
            either = Either.left(new ErrorC(8, Constants.SQL_ERROR + sqle.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> deleteJDBC(Order order) {
        Either<ErrorC, Integer> either;
        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement deleteOrderItemsStatement = con.prepareStatement(SQLQueries.DELETE_ORDER_ITEMS_BY_ORDERS)) {
                deleteOrderItemsStatement.setInt(1, order.getOrderId());
                deleteOrderItemsStatement.executeUpdate();

                try (PreparedStatement deleteOrderStatement = con.prepareStatement(SQLQueries.DELETE_ORDER)) {
                    deleteOrderStatement.setInt(1, order.getOrderId());
                    int rowsAffected = deleteOrderStatement.executeUpdate();

                    con.commit();
                    con.setAutoCommit(true);

                    either = Either.right(rowsAffected);
                }
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                either = Either.left(new ErrorC(7, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
            }
        } catch (SQLException sqle) {
            either = Either.left(new ErrorC(7, Constants.SQL_ERROR + sqle.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
