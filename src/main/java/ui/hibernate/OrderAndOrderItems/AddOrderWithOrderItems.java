package ui.hibernate.OrderAndOrderItems;

import domain.model.hibernate.MenuItemH;
import domain.model.hibernate.OrderH;
import domain.model.hibernate.OrderItemH;
import domain.services.hibernate.OrderService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddOrderWithOrderItems {

    private final OrderService orderService;

    @Inject
    public AddOrderWithOrderItems(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        AddOrderWithOrderItems addOrderWithOrderItems = container.select(AddOrderWithOrderItems.class).get();

        MenuItemH menuItemH = new MenuItemH(1,"Spaghetti Carbonara","Creamy pasta with bacon and Parmesan cheese",12.99);
        List<OrderItemH> orderItemHS = new ArrayList<>();
        OrderItemH orderItemH1 = new OrderItemH(null,null, menuItemH,10);
        orderItemHS.add(orderItemH1);

        OrderH newOrderH = new OrderH(null, LocalDateTime.now(), 10,1, orderItemHS);
        int addedOrder = addOrderWithOrderItems.orderService.add(newOrderH).getOrElse(2);
        System.out.println(addedOrder);
    }
}


