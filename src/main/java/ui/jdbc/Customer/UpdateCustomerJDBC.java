package ui.jdbc.Customer;

import domain.model.springJDBC.Credentials;
import domain.model.springJDBC.Customer;
import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

import java.time.LocalDate;

public class UpdateCustomerJDBC {

    private final CustomerServiceSJ customerService;

    @Inject
    public UpdateCustomerJDBC(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateCustomerJDBC updateCustomerJDBC = container.select(UpdateCustomerJDBC.class).get();

        Credentials credentials = new Credentials(55, "z", "z");
        Customer customer = new Customer(55,"z","zzz","z","454353", LocalDate.now(), credentials);
        System.out.println(updateCustomerJDBC.customerService.updateJDBC(customer));
    }
}


