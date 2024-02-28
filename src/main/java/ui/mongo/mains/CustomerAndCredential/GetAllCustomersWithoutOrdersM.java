package ui.mongo.mains.CustomerAndCredential;

import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllCustomersWithoutOrdersM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public GetAllCustomersWithoutOrdersM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllCustomersWithoutOrdersM getAllCustomersWithoutOrdersM = container.select(GetAllCustomersWithoutOrdersM.class).get();
        System.out.println(getAllCustomersWithoutOrdersM.customerServiceM.getAllWithoutOrders());
    }
}


