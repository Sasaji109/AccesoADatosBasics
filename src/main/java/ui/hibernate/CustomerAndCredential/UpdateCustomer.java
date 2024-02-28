package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.Credentials;
import domain.model.hibernate.Customers;
import domain.services.hibernate.CustomerService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class UpdateCustomer {

    private final CustomerService customerService;

    @Inject
    public UpdateCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateCustomer updateCustomer = container.select(UpdateCustomer.class).get();

        Credentials credentials = new Credentials(52, "ccc","c");
        Customers customer = new Customers(52,"ccc","c","gmail","3425252", LocalDate.now(), credentials);
        int rigth = updateCustomer.customerService.update(customer).get();
        System.out.println(rigth);
    }
}


