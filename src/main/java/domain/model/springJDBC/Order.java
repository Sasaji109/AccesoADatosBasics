package domain.model.springJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private Integer orderId;
    private LocalDateTime orderDate;
    private Integer customerId;
    private Integer tableId;
    private List<OrderItem> orderItemList;

    public Order() {}

    public String toStringTextFile() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(orderId).append(";")
                .append(orderDate).append(";")
                .append(customerId).append(";")
                .append(tableId);

        if (orderItemList != null) {
            for (OrderItem orderItem : orderItemList) {
                stringBuilder.append("\n").append(orderItem.toStringTextFile());
            }
        }

        return stringBuilder.toString();
    }
}
