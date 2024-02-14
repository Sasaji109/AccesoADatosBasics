package ui.mongo.mains.CustomerAndCredential;

import domain.services.mongo.CredentialsServiceM;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

public class DeleteCustomerM {

    private final CustomerServiceM customerServiceM;
    private final CredentialsServiceM credentialsServiceM;

    @Inject
    public DeleteCustomerM(CustomerServiceM customerServiceM, CredentialsServiceM credentialsServiceM) {
        this.customerServiceM = customerServiceM;
        this.credentialsServiceM = credentialsServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteCustomerM deleteCustomerM = container.select(DeleteCustomerM.class).get();

        ObjectId objectIdCred = new ObjectId("65c28f5b941c002bf4e18b9d");
        int deletedCredentials = deleteCustomerM.credentialsServiceM.delete(objectIdCred).get();

        ObjectId objectIdCust = new ObjectId("65c285e0789f9d78fda81290");
        int deletedCustomer = deleteCustomerM.customerServiceM.delete(objectIdCust).get();

        System.out.println(deletedCredentials);
        System.out.println(deletedCustomer);
    }
}


