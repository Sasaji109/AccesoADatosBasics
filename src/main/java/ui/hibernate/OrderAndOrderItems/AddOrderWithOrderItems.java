package ui.hibernate.OrderAndOrderItems;

import domain.model.hibernate.MenuItem;
import domain.model.hibernate.Order;
import domain.model.hibernate.OrderItem;
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

        MenuItem menuItem = new MenuItem(1,"Spaghetti Carbonara","Creamy pasta with bacon and Parmesan cheese",12.99);
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem(null,null,menuItem,10);
        orderItems.add(orderItem1);

        Order newOrder = new Order(null, LocalDateTime.now(), 50,1, orderItems);
        int addedOrder = addOrderWithOrderItems.orderService.add(newOrder).getOrElse(2);
        System.out.println(addedOrder);
    }
}


