package domain.model.mongo;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderMongo {
    private String order_date;
    private Integer table_id;
    private List<OrderItemMongo> order_items;
}
