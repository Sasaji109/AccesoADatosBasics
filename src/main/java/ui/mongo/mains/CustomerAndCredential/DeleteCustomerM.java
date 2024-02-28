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

public class DeleteCustomerM {

    private final CustomerServiceM customerServiceM;

    @Inject
    public DeleteCustomerM(CustomerServiceM customerServiceM) {
        this.customerServiceM = customerServiceM;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        DeleteCustomerM deleteCustomerM = container.select(DeleteCustomerM.class).get();


        ObjectId objectId = new ObjectId("65df0978f6296e1888be1eb3");
        List<OrderMongo> orders = Collections.emptyList();
        CustomersMongo newCustomer = new CustomersMongo(objectId,"c","ccc","gmail","342352","2002-03-03", orders);
        int deletedCustomer = deleteCustomerM.customerServiceM.delete(newCustomer).get();
        System.out.println(deletedCustomer);
    }
}


