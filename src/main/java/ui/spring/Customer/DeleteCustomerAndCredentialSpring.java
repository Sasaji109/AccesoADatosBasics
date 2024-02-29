package ui.spring.Customer;

import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class DeleteCustomerAndCredentialSpring {

    private final CustomerServiceSJ customerService;

    @Inject
    public DeleteCustomerAndCredentialSpring(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteCustomerAndCredentialSpring deleteCustomerAndCredentialSpring = container.select(DeleteCustomerAndCredentialSpring.class).get();
        System.out.println(deleteCustomerAndCredentialSpring.customerService.deleteSpring(56, false));
    }
}


