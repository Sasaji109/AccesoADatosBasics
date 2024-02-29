package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.CredentialsH;
import domain.model.hibernate.CustomersH;
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

        CredentialsH credentialsH = new CredentialsH(51, "c","c");
        CustomersH customer = new CustomersH(51,"c","c","gmail","3425252", LocalDate.now(), credentialsH);
        int rigth = deleteCustomerNoOrders.customerService.delete(customer, false).get();
        System.out.println(rigth);
    }
}


