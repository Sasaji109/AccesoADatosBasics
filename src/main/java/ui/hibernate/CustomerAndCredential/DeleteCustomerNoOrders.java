package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.Credentials;
import domain.model.hibernate.Customers;
import domain.services.hibernate.CustomerService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class DeleteCustomerNoOrders {

    private final CustomerService customerService;

    @Inject
    public DeleteCustomerNoOrders(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteCustomerNoOrders deleteCustomerNoOrders = container.select(DeleteCustomerNoOrders.class).get();

        Credentials credentials = new Credentials(48, "sam","sam");
        Customers customer = new Customers(48,"Sam","San","gmail","3452525", LocalDate.now(), credentials);
        int rigth = deleteCustomerNoOrders.customerService.delete(customer, false).get();
        System.out.println(rigth);
    }
}


