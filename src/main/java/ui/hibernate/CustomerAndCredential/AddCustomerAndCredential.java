package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.Credentials;
import domain.model.hibernate.Customers;
import domain.services.hibernate.CustomerService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class AddCustomerAndCredential {

    private final CustomerService customerService;

    @Inject
    public AddCustomerAndCredential(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        AddCustomerAndCredential addCustomerAndCredential = container.select(AddCustomerAndCredential.class).get();

        Credentials credentials = new Credentials(null, "c","c");
        Customers newCustomer = new Customers(null,"c","c","gmail","3425252", LocalDate.now(), credentials);
        addCustomerAndCredential.customerService.add(newCustomer);
        System.out.println("Nuevo customer añadido: " + newCustomer);
    }
}


