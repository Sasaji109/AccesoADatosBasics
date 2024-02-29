package ui.jdbc.Customer;

import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetAllCustomersJDBC {

    private final CustomerServiceSJ customerService;

    @Inject
    public GetAllCustomersJDBC(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllCustomersJDBC getAllCustomersAndCredentialsSpring = container.select(GetAllCustomersJDBC.class).get();
        System.out.println(getAllCustomersAndCredentialsSpring.customerService.getAllJDBC());
    }
}


