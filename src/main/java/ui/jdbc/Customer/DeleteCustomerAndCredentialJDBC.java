package ui.jdbc.Customer;

import domain.model.springJDBC.Credentials;
import domain.model.springJDBC.Customer;
import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.time.LocalDate;

public class DeleteCustomerAndCredentialJDBC {

    private final CustomerServiceSJ customerService;

    @Inject
    public DeleteCustomerAndCredentialJDBC(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteCustomerAndCredentialJDBC deleteCustomerAndCredentialJDBC = container.select(DeleteCustomerAndCredentialJDBC.class).get();
        System.out.println(deleteCustomerAndCredentialJDBC.customerService.deleteJDBC(55, false));
    }
}


