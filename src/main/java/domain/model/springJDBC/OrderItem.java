package domain.model.springJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private MenuItem menuItem;
    private Integer quantity;

    public OrderItem() {}

    public String toStringTextFile() {
        return id + ";" + orderId + ";" + menuItem.toStringTextFile() + ";" + quantity;
    }
}
