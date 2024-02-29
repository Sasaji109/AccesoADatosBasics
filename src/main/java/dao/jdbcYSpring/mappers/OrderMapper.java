package dao.jdbcYSpring.mappers;

import domain.model.springJDBC.Order;
import domain.model.springJDBC.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setCustomerId(rs.getInt("customer_id"));
        order.setTableId(rs.getInt("table_id"));
        List<OrderItem> orderItems = new ArrayList<>();
        order.setOrderItemList(orderItems);
        return order;
    }
}
