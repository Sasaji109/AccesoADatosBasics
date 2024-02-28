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

public class DeleteOrderWithOrderItems {

    private final OrderService orderService;

    @Inject
    public DeleteOrderWithOrderItems(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteOrderWithOrderItems deleteOrderWithOrderItems = container.select(DeleteOrderWithOrderItems.class).get();

        MenuItem menuItem = new MenuItem(1,"Spaghetti Carbonara","Creamy pasta with bacon and Parmesan cheese",12.99);
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem(35,46,menuItem,11);
        orderItems.add(orderItem1);

        Order order = new Order(46, LocalDateTime.now(), 10,4, orderItems);
        int addedOrder = deleteOrderWithOrderItems.orderService.delete(order).getOrElse(2);
        System.out.println(addedOrder);
    }
}


