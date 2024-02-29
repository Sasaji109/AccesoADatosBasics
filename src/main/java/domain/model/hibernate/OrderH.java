package domain.model.hibernate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "orders", schema = "example_exam_2eva")
public class OrderH {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "table_id")
    private Integer tableId;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderItemH> orderItemHList;

    public OrderH(Integer orderId, LocalDateTime orderDate, Integer customerId, Integer tableId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.tableId = tableId;
    }

    public OrderH(Integer orderId, LocalDateTime orderDate, Integer customerId, Integer tableId, List<OrderItemH> orderItemHList) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.tableId = tableId;
        this.orderItemHList = orderItemHList;
    }
}
