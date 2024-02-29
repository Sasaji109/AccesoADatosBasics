package ui.jdbc.Customer;

import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetCustomerJDBC {

    private final CustomerServiceSJ customerService;

    @Inject
    public GetCustomerJDBC(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetCustomerJDBC getCustomerJDBC = container.select(GetCustomerJDBC.class).get();
        System.out.println(getCustomerJDBC.customerService.getJDBC(1));
    }
}


