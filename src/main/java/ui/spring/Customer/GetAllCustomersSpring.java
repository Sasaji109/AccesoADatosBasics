package ui.spring.Customer;

import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllCustomersSpring {

    private final CustomerServiceSJ customerService;

    @Inject
    public GetAllCustomersSpring(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllCustomersSpring getAllCustomersSpring = container.select(GetAllCustomersSpring.class).get();
        System.out.println(getAllCustomersSpring.customerService.getAllSpring());
    }
}


