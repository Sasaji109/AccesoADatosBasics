package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.CredentialsH;
import domain.model.hibernate.CustomersH;
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

        CredentialsH credentialsH = new CredentialsH(null, "c","c");
        CustomersH newCustomer = new CustomersH(null,"c","c","gmail","3425252", LocalDate.now(), credentialsH);
        addCustomerAndCredential.customerService.add(newCustomer);
        System.out.println("Nuevo customer añadido: " + newCustomer);
    }
}


