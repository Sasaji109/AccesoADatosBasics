package ui.XMLyTXT;

import domain.model.springJDBC.Order;
import domain.services.XMLyTXT.BackupServices;
import domain.services.transferData.TransferServices;
import domain.xml.OrderXML;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class BackupOrdersXML {

    private final BackupServices backupServices;

    @Inject
    public BackupOrdersXML(BackupServices backupServices) {
        this.backupServices = backupServices;
    }

    public static void main(String[] args) throws Exception {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        BackupOrdersXML backupOrdersXML = container.select(BackupOrdersXML.class).get();

        List<OrderXML> orders = new ArrayList<>();
        boolean resultado = backupOrdersXML.backupServices.backupOrdersToXml(orders, "Prueba");
        System.out.println(resultado);
    }
}


