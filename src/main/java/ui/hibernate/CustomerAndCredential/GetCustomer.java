package ui.hibernate.CustomerAndCredential;

import domain.services.hibernate.CustomerService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
public class GetCustomer {

    private final CustomerService customerService;

    @Inject
    public GetCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetCustomer getCustomer = container.select(GetCustomer.class).get();
        System.out.println(getCustomer.customerService.get(50));
    }
}


