package ui.hibernate.OrderAndOrderItems;

import domain.model.hibernate.MenuItem;
import domain.model.hibernate.Order;
import domain.model.hibernate.OrderItem;
import domain.model.mongo.OrderItemMongo;
import domain.model.mongo.OrderMongo;
import domain.services.hibernate.OrderService;
import domain.services.mongo.OrderServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateOrderWithOrderItems {

    private final OrderService orderService;

    @Inject
    public UpdateOrderWithOrderItems(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateOrderWithOrderItems updateOrderWithOrderItems = container.select(UpdateOrderWithOrderItems.class).get();

        MenuItem menuItem = new MenuItem(1,"Spaghetti Carbonara","Creamy pasta with bacon and Parmesan cheese",12.99);
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem(33,44,menuItem,11);
        orderItems.add(orderItem1);

        Order order = new Order(44, LocalDateTime.now(), 50,3, orderItems);
        int updatedOrder = updateOrderWithOrderItems.orderService.update(order).getOrElse(2);
        System.out.println(updatedOrder);
    }
}


