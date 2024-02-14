package domain.model.hibernate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_items", schema = "example_exam_2eva")
public class OrderItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_item_id")
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(name = "quantity")
    private Integer quantity;

    public OrderItem(Integer id, Integer orderId, MenuItem menuItem, Integer quantity) {
        this.id = id;
        this.orderId = orderId;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
}
