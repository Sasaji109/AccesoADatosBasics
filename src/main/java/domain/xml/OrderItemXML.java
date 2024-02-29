package domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "orderItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemXML {

    @XmlElement(name = "id")
    private Integer id;

    @XmlElement(name = "orderId")
    private Integer orderId;

    @XmlElement(name = "menuItem")
    private MenuItemXML menuItemXML;

    @XmlElement(name = "quantity")
    private Integer quantity;
}
