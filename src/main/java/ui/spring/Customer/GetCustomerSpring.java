package ui.spring.Customer;

import domain.services.jdbcYSpring.CustomerServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
public class GetCustomerSpring {

    private final CustomerServiceSJ customerService;

    @Inject
    public GetCustomerSpring(CustomerServiceSJ customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetCustomerSpring getCustomerJDBC = container.select(GetCustomerSpring.class).get();
        System.out.println(getCustomerJDBC.customerService.getSpring(1));
    }
}


