package ui.XMLyTXT;

import domain.services.XMLyTXT.BackupServices;
import domain.model.xml.MenuItemXML;
import domain.model.xml.OrderItemXML;
import domain.model.xml.OrderXML;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
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

        MenuItemXML menuItemXML = new MenuItemXML(1,"A","A", 0.0);
        OrderItemXML orderItemXML1 = new OrderItemXML(1,1,menuItemXML,10);
        List<OrderItemXML> orderItemXMLList = new ArrayList<>();
        orderItemXMLList.add(orderItemXML1);
        List<OrderXML> orders = new ArrayList<>();
        OrderXML orderXMl1 = new OrderXML(1, LocalDateTime.now(),1,2,orderItemXMLList);
        orders.add(orderXMl1);
        boolean resultado = backupOrdersXML.backupServices.backupOrdersToXml(orders, "PruebaXML");
        System.out.println(resultado);
    }
}


