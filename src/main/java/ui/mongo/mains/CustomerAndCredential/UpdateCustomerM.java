package ui.mongo.mains.CustomerAndCredential;

import domain.model.mongo.CustomersMongo;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

public class UpdateCustomerM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public UpdateCustomerM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateCustomerM updateCustomerM = container.select(UpdateCustomerM.class).get();

        ObjectId objectId = new ObjectId("65e0bcca3b99ec52af137c71");
        CustomersMongo customer = updateCustomerM.customerServiceM.get(objectId).get();
        customer.setLast_name("ccc");
        int updateCustomerInt = updateCustomerM.customerServiceM.update(customer).getOrElse(2);
        System.out.println(updateCustomerInt);
    }
}


