package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.CredentialsH;
import domain.model.hibernate.CustomersH;
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

        CredentialsH credentialsH = new CredentialsH(52, "ccc","c");
        CustomersH customer = new CustomersH(52,"ccc","c","gmail","3425252", LocalDate.now(), credentialsH);
        int rigth = updateCustomer.customerService.update(customer).get();
        System.out.println(rigth);
    }
}


