package ui.transferData;

import domain.services.transferData.TransferServices;
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

        int rigth = mongoToHibernate.transferServices.transferMongoToHibernate().getOrElse(2);
        System.out.println(rigth);
    }
}


