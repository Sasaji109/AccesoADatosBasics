package ui.XMLyTXT;

import domain.model.springJDBC.MenuItem;
import domain.model.springJDBC.Order;
import domain.model.springJDBC.OrderItem;
import domain.services.XMLyTXT.BackupServices;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BackupOrderTXT {

    private final BackupServices backupServices;

    @Inject
    public BackupOrderTXT(BackupServices backupServices) {
        this.backupServices = backupServices;
    }

    public static void main(String[] args) throws Exception {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        BackupOrderTXT backupOrderTXT = container.select(BackupOrderTXT.class).get();

        MenuItem menuItem = new MenuItem(2,"Maccaroni","Delicious", 10.0);
        OrderItem orderItem1 = new OrderItem(2,2,menuItem,100);
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem1);
        Order order = new Order(2, LocalDateTime.now(),1,5,orderItemList);
        System.out.println(backupOrderTXT.backupServices.saveOrderInTXT(order));
    }
}


