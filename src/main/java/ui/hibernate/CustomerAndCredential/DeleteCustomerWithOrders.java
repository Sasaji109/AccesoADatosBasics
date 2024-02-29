package ui.hibernate.CustomerAndCredential;

import domain.model.hibernate.CredentialsH;
import domain.model.hibernate.CustomersH;
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

        CredentialsH credentialsH = new CredentialsH(47, "sam","sam");
        CustomersH customer = new CustomersH(47,"Sam","San","gmail","3452525", LocalDate.now(), credentialsH);
        int right = deleteCustomer.customerService.delete(customer, true).get();
        System.out.println(right);
    }
}


