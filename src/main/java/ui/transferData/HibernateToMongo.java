package ui.transferData;

import domain.services.transferData.TransferServices;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class HibernateToMongo {

    private final TransferServices transferServices;

    @Inject
    public HibernateToMongo(TransferServices transferServices) {
        this.transferServices = transferServices;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        HibernateToMongo hibernateToMongo = container.select(HibernateToMongo.class).get();

        int rigth = hibernateToMongo.transferServices.transferHibernateToMongo().getOrElse(2);
        System.out.println(rigth);
    }
}


