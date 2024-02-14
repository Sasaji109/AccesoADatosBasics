package ui.hibernate.OrderAndOrderItems;

import domain.services.hibernate.OrderService;
import domain.services.mongo.OrderServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllOrdersAndOrderItems {

    private final OrderService orderService;

    @Inject
    public GetAllOrdersAndOrderItems(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllOrdersAndOrderItems getAllOrdersAndOrderItems = container.select(GetAllOrdersAndOrderItems.class).get();
        System.out.println(getAllOrdersAndOrderItems.orderService.getAll());
    }
}


