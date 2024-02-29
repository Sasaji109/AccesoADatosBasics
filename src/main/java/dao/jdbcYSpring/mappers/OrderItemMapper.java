package dao.jdbcYSpring.mappers;

import domain.model.springJDBC.MenuItem;
import domain.model.springJDBC.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        MenuItem menuItem = new MenuItem();
        menuItem.setId(rs.getInt("menu_item_id"));
        menuItem.setName(rs.getString("name"));
        menuItem.setDescription(rs.getString("description"));
        menuItem.setPrice(rs.getDouble("price"));
        orderItem.setMenuItem(menuItem);

        orderItem.setQuantity(rs.getInt("quantity"));
        return orderItem;
    }
}
