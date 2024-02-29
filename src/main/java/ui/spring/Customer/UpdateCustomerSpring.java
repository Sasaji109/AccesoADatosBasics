package ui.spring.Customer;

import domain.model.springJDBC.Credentials;
import domain.model.springJDBC.Customer;
import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class UpdateCustomerSpring {

    private final CustomerServiceSJ customerService;

    @Inject
    public UpdateCustomerSpring(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateCustomerSpring updateCustomerSpring = container.select(UpdateCustomerSpring.class).get();

        Credentials credentials = new Credentials(56, "z", "z");
        Customer customer = new Customer(56,"z","zzz","z","454353", LocalDate.now(), credentials);
        System.out.println(updateCustomerSpring.customerService.updateSpring(customer));
    }
}


