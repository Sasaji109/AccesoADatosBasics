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

public class UpdateOrderWithOrderItems {

    private final OrderService orderService;

    @Inject
    public UpdateOrderWithOrderItems(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateOrderWithOrderItems updateOrderWithOrderItems = container.select(UpdateOrderWithOrderItems.class).get();

        MenuItemH menuItemH = new MenuItemH(1,"Spaghetti Carbonara","Creamy pasta with bacon and Parmesan cheese",12.99);
        List<OrderItemH> orderItemHS = new ArrayList<>();
        OrderItemH orderItemH1 = new OrderItemH(35,46, menuItemH,11);
        orderItemHS.add(orderItemH1);

        OrderH orderH = new OrderH(46, LocalDateTime.now(), 10,4, orderItemHS);
        int updatedOrder = updateOrderWithOrderItems.orderService.update(orderH).getOrElse(2);
        System.out.println(updatedOrder);
    }
}


