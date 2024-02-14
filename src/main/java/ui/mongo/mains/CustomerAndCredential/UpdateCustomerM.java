package ui.mongo.mains.CustomerAndCredential;

import domain.model.mongo.CustomersMongo;
import domain.model.mongo.OrderMongo;
import domain.services.mongo.CustomerServiceM;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.Collections;
import java.util.List;

public class UpdateCustomerM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public UpdateCustomerM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        UpdateCustomerM updateCustomerM = container.select(UpdateCustomerM.class).get();

        ObjectId objectIdCust = new ObjectId("65c247641544345e6e5e9639");
        List<OrderMongo> orders = Collections.emptyList();
        CustomersMongo newCustomer = new CustomersMongo(objectIdCust,"Sam","San","sam","3452525","2002-03-03", orders);
        int updateCustomerInt = updateCustomerM.customerServiceM.update(newCustomer).get();
        System.out.println(updateCustomerInt);
    }
}


