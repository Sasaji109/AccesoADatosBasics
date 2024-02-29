package ui.jdbc.OrderItems;

import domain.services.jdbcYSpring.OrderItemsServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllOrderItemsJDBC {

    private final OrderItemsServiceSJ orderItemsServiceSJ;

    @Inject
    public GetAllOrderItemsJDBC(OrderItemsServiceSJ orderItemsServiceSJ) {
        this.orderItemsServiceSJ = orderItemsServiceSJ;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllOrderItemsJDBC getAllOrderItemsJDBC = container.select(GetAllOrderItemsJDBC.class).get();
        System.out.println(getAllOrderItemsJDBC.orderItemsServiceSJ.getAllJDBC());
    }
}


