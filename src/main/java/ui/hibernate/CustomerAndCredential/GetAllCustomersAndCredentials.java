package ui.hibernate.CustomerAndCredential;

import domain.services.hibernate.CustomerService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllCustomersAndCredentials {

    private final CustomerService customerService;

    @Inject
    public GetAllCustomersAndCredentials(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllCustomersAndCredentials getAllCustomersAndCredentials = container.select(GetAllCustomersAndCredentials.class).get();
        System.out.println(getAllCustomersAndCredentials.customerService.getAll());
    }
}


