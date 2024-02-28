package ui.transferData;

import domain.model.ErrorC;
import domain.services.transferData.TransferServices;
import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class MongoToHibernate {

    private final TransferServices transferServices;

    @Inject
    public MongoToHibernate(TransferServices transferServices) {
        this.transferServices = transferServices;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        MongoToHibernate mongoToHibernate = container.select(MongoToHibernate.class).get();

        Either<ErrorC, Integer> result = mongoToHibernate.transferServices.transferMongoToHibernate();

        if (result.isRight()) {
            int rightValue = result.getOrElse(10);
            System.out.println("Operation successful, value: " + rightValue);
        } else {
            ErrorC error = result.getLeft();
            System.out.println("Operation failed with error: " + error.toString());
        }
    }

}


