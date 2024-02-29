package domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderXML {

    @XmlElement(name = "orderId")
    private Integer orderId;

    @XmlElement(name = "orderDate")
    private LocalDateTime orderDate;

    @XmlElement(name = "customerId")
    private Integer customerId;

    @XmlElement(name = "tableId")
    private Integer tableId;

    @XmlElement(name = "orderItem")
    private List<OrderItemXML> orderItemHList;
}
