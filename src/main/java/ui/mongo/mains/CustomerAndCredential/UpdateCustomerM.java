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

        ObjectId objectId = new ObjectId("65cca33388aedd402cc8da57");
        List<OrderMongo> orders = Collections.emptyList();
        CustomersMongo newCustomer = new CustomersMongo(objectId,"Gio","gio","gmail","34525352525","2002-03-03", orders);
        int updateCustomerInt = updateCustomerM.customerServiceM.update(newCustomer).getOrElse(2);
        System.out.println(updateCustomerInt);
    }
}


