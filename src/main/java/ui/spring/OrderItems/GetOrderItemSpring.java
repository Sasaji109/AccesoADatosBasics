package ui.spring.OrderItems;

import domain.services.jdbcYSpring.OrderItemsServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetOrderItemSpring {

    private final OrderItemsServiceSJ orderItemsServiceSJ;

    @Inject
    public GetOrderItemSpring(OrderItemsServiceSJ orderItemsServiceSJ) {
        this.orderItemsServiceSJ = orderItemsServiceSJ;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetOrderItemSpring getAllOrderItemsSpring = container.select(GetOrderItemSpring.class).get();
        System.out.println(getAllOrderItemsSpring.orderItemsServiceSJ.getSpring(1));
    }
}


