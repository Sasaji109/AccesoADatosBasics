package ui.jdbc.OrderAndOrderItems;

import domain.services.jdbcYSpring.OrderServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllOrdersAndOrderItems {

    private final OrderServiceSJ orderService;

    @Inject
    public GetAllOrdersAndOrderItems(OrderServiceSJ orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllOrdersAndOrderItems getAllOrdersAndOrderItems = container.select(GetAllOrdersAndOrderItems.class).get();
        System.out.println(getAllOrdersAndOrderItems.orderService.getAllSpring());
    }
}


