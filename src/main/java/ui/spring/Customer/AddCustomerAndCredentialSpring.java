package ui.spring.Customer;

import domain.model.springJDBC.Credentials;
import domain.model.springJDBC.Customer;
import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class AddCustomerAndCredentialSpring {

    private final CustomerServiceSJ customerService;

    @Inject
    public AddCustomerAndCredentialSpring(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        AddCustomerAndCredentialSpring addCustomerAndCredentialJDBC = container.select(AddCustomerAndCredentialSpring.class).get();

        Credentials credentials = new Credentials(0, "z", "z");
        Customer customer = new Customer(0,"z","z","z","454353", LocalDate.now(), credentials);
        System.out.println(addCustomerAndCredentialJDBC.customerService.addSpring(customer));
    }
}


