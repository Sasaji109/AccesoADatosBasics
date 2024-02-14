package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.Credentials;
import domain.model.hibernate.Customers;
import domain.services.hibernate.CustomerService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class DeleteCustomerWithOrders {

    private final CustomerService customerService;

    @Inject
    public DeleteCustomerWithOrders(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteCustomerWithOrders deleteCustomer = container.select(DeleteCustomerWithOrders.class).get();

        Credentials credentials = new Credentials(47, "sam","sam");
        Customers customer = new Customers(47,"Sam","San","gmail","3452525", LocalDate.now(), credentials);
        int right = deleteCustomer.customerService.delete(customer, true).get();
        System.out.println(right);
    }
}


