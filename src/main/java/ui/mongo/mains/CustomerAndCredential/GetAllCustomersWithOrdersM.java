package ui.mongo.mains.CustomerAndCredential;

import domain.model.mongo.CustomersMongo;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.util.List;

public class GetAllCustomersWithOrdersM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public GetAllCustomersWithOrdersM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllCustomersWithOrdersM getAllCustomersM = container.select(GetAllCustomersWithOrdersM.class).get();
        List<CustomersMongo> customers = getAllCustomersM.customerServiceM.getAll().get();
        System.out.println(customers.toString());
    }
}


