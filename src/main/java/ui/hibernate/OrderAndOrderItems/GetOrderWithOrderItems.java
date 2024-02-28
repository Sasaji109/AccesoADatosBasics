package ui.hibernate.OrderAndOrderItems;

import domain.services.hibernate.OrderService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetOrderWithOrderItems {

    private final OrderService orderService;

    @Inject
    public GetOrderWithOrderItems(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetOrderWithOrderItems getOrderWithOrderItems = container.select(GetOrderWithOrderItems.class).get();
        System.out.println(getOrderWithOrderItems.orderService.get(1));
    }
}


