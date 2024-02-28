package ui.mongo.mains.CustomerAndCredential;

import domain.model.mongo.CustomersMongo;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

public class GetCustomerM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public GetCustomerM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetCustomerM getCustomerM = container.select(GetCustomerM.class).get();
        ObjectId objectId = new ObjectId("65cc901f2c6b79707fcd6c18");
        CustomersMongo customer = getCustomerM.customerServiceM.get(objectId).get();
        System.out.println(customer.toString());
    }
}


