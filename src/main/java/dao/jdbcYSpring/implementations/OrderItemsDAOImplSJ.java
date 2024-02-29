package dao.jdbcYSpring.implementations;

import common.configuration.DBConnection;
import common.uitls.Constants;
import common.uitls.SQLQueries;
import dao.jdbcYSpring.OrderItemsDAOSJ;
import dao.jdbcYSpring.mappers.OrderItemMapper;
import domain.model.springJDBC.MenuItem;
import domain.model.springJDBC.OrderItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDAOImplSJ implements OrderItemsDAOSJ {

    private final DBConnection db;

    @Inject
    public OrderItemsDAOImplSJ(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<ErrorC, List<OrderItem>> getAllJDBC() {
        Either<ErrorC, List<OrderItem>> either;

        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ORDERITEMS);

            while (rs.next()) {
                int orderItemId = rs.getInt("order_item_id");
                int orderId = rs.getInt("order_id");
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getInt("menu_item_id"));
                menuItem.setName(rs.getString("name"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getDouble("price"));
                int quantity = rs.getInt("quantity");

                OrderItem orderItem = new OrderItem(orderItemId,orderId,menuItem,quantity);
                orderItems.add(orderItem);
            }
            either = Either.right(orderItems);
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(3, Constants.ERROR_ON_LOADING_CUSTOMERS, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, List<OrderItem>> getAllSpring() {
        Either<ErrorC, List<OrderItem>> either;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            List<OrderItem> orderItems = jtm.query(SQLQueries.SELECT_ORDERITEMS, new OrderItemMapper());
            either = Either.right(orderItems);
        } catch (Exception ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, OrderItem> getJDBC(Integer id) {
        Either<ErrorC, OrderItem> either;

        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ORDERITEM_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int orderItemId = rs.getInt("order_item_id");
                int orderId = rs.getInt("order_id");
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getInt("menu_item_id"));
                menuItem.setName(rs.getString("name"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getDouble("price"));
                int quantity = rs.getInt("quantity");

                OrderItem foundOrderItem = new OrderItem(orderItemId,orderId,menuItem,quantity);
                either = Either.right(foundOrderItem);
            } else {
                either = Either.left(new ErrorC(4, Constants.ORDER_ITEM_NOT_FOUND, LocalDate.now()));
            }
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(3, Constants.ERROR_ON_RETRIEVING_THE_ORDER_ITEM, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, OrderItem> getSpring(Integer id) {
        Either<ErrorC, OrderItem> either;
        try {
            JdbcTemplate jtm = new JdbcTemplate(db.getDataSource());
            OrderItem orderItem = jtm.queryForObject(SQLQueries.SELECT_ORDERITEM_BY_ID, new Object[]{id}, new OrderItemMapper());
            either = Either.right(orderItem);
        } catch (Exception ex) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + ex.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> deleteJDBC(OrderItem orderItem) {
        Either<ErrorC, Integer> either;
        try (Connection con = db.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement deleteOrderItemStatement = con.prepareStatement(SQLQueries.DELETE_ORDERITEM)) {
                deleteOrderItemStatement.setInt(1, orderItem.getId());
                int rowsAffected = deleteOrderItemStatement.executeUpdate();

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
        return either;
    }
}

