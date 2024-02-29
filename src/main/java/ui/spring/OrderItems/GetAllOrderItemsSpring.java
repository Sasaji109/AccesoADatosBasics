package ui.spring.OrderItems;

import domain.services.jdbcYSpring.OrderItemsServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllOrderItemsSpring {

    private final OrderItemsServiceSJ orderItemsServiceSJ;

    @Inject
    public GetAllOrderItemsSpring(OrderItemsServiceSJ orderItemsServiceSJ) {
        this.orderItemsServiceSJ = orderItemsServiceSJ;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllOrderItemsSpring getAllOrderItemsSpring = container.select(GetAllOrderItemsSpring.class).get();
        System.out.println(getAllOrderItemsSpring.orderItemsServiceSJ.getAllSpring());
    }
}


