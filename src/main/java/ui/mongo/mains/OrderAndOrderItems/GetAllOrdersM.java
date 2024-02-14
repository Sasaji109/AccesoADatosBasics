package ui.mongo.mains.OrderAndOrderItems;

import domain.services.mongo.OrderServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllOrdersM {

    private final OrderServiceM orderServiceM;

    @Inject
    public GetAllOrdersM(OrderServiceM orderServiceM) {
        this.orderServiceM = orderServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllOrdersM getAllOrdersM = container.select(GetAllOrdersM.class).get();
        System.out.println(getAllOrdersM.orderServiceM.getAll());
    }
}


