package ui.mongo.mains.CustomerAndCredential;

import domain.model.mongo.CredentialsMongo;
import domain.model.mongo.CustomersMongo;
import domain.model.mongo.OrderMongo;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import java.util.Collections;
import java.util.List;

public class AddCustomerAndCredentialM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public AddCustomerAndCredentialM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        AddCustomerAndCredentialM addCustomerAndCredentialM = container.select(AddCustomerAndCredentialM.class).get();

        CredentialsMongo newCredentials = new CredentialsMongo(null, "c", "c");
        List<OrderMongo> orders = Collections.emptyList();
        CustomersMongo newCustomer = new CustomersMongo(null,"c","c","gmail","342352","2002-03-03", orders, newCredentials);
        CustomersMongo addedCustomer = addCustomerAndCredentialM.customerServiceM.add(newCustomer).get();
        System.out.println("Nuevo customer añadido: " + addedCustomer);
    }
}


