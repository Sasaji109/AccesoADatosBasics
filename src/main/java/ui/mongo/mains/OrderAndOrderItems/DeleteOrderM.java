package ui.mongo.mains.OrderAndOrderItems;

import domain.model.mongo.OrderItemMongo;
import domain.model.mongo.OrderMongo;
import domain.services.mongo.OrderServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.Collections;
import java.util.List;

public class DeleteOrderM {

    private final OrderServiceM orderServiceM;

    @Inject
    public DeleteOrderM(OrderServiceM orderServiceM) {
        this.orderServiceM = orderServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteOrderM deleteOrderM = container.select(DeleteOrderM.class).get();

        List<OrderItemMongo> orderItems = Collections.emptyList();
        OrderMongo newOrder = new OrderMongo("2004-02-02", 8, orderItems);
        ObjectId objectIdCust = new ObjectId("65cca33388aedd402cc8da57");
        int deletedCustomer = deleteOrderM.orderServiceM.delete(newOrder, objectIdCust).getOrElse(2);
        System.out.println(deletedCustomer);
    }
}


