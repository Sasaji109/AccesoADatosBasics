package ui.mongo.mains.CustomerAndCredential;

import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllCustomersM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public GetAllCustomersM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllCustomersM getAllCustomersM = container.select(GetAllCustomersM.class).get();
        System.out.println(getAllCustomersM.customerServiceM.getAll());
    }
}


