package ui.mongo.mains.CustomerAndCredential;

import domain.model.mongo.CredentialsMongo;
import domain.model.mongo.CustomersMongo;
import domain.model.mongo.OrderMongo;
import domain.services.mongo.CredentialsServiceM;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Collections;
import java.util.List;

public class AddCustomerM {

    private final CustomerServiceM customerServiceM;
    private final CredentialsServiceM credentialsServiceM;

    @Inject
    public AddCustomerM(CustomerServiceM customerServiceM, CredentialsServiceM credentialsServiceM) {
        this.customerServiceM = customerServiceM;
        this.credentialsServiceM = credentialsServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        AddCustomerM addCustomerM = container.select(AddCustomerM.class).get();

        CredentialsMongo newCredentials = new CredentialsMongo(null, "sam", "sam");
        Document document = addCustomerM.credentialsServiceM.add(newCredentials).get();
        ObjectId insertedObjectId = document.getObjectId("_id");

        List<OrderMongo> orders = Collections.emptyList();
        CustomersMongo newCustomer = new CustomersMongo(insertedObjectId,"Sam","San","gmail","3452525","2002-03-03", orders);
        CustomersMongo addedCustomer = addCustomerM.customerServiceM.add(newCustomer).get();

        System.out.println("Nuevo credential añadido: " + document);
        System.out.println("Nuevo customer añadido: " + addedCustomer);
    }
}


