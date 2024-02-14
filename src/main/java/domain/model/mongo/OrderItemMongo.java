package domain.model.mongo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemMongo {
    private Integer menu_item_id;
    private Integer quantity;
}
