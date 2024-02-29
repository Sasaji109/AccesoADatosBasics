package ui.jdbc.OrderItems;

import domain.services.jdbcYSpring.OrderItemsServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetOrderItemJDBC {

    private final OrderItemsServiceSJ orderItemsServiceSJ;

    @Inject
    public GetOrderItemJDBC(OrderItemsServiceSJ orderItemsServiceSJ) {
        this.orderItemsServiceSJ = orderItemsServiceSJ;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetOrderItemJDBC getOrderItemJDBC = container.select(GetOrderItemJDBC.class).get();
        System.out.println(getOrderItemJDBC.orderItemsServiceSJ.getJDBC(1));
    }
}


