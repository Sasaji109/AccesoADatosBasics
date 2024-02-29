package ui.jdbc.MenuItem;

import domain.services.jdbcYSpring.MenuItemsServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
public class GetAllMenuItems {

    private final MenuItemsServiceSJ menuItemsServiceSJ;

    @Inject
    public GetAllMenuItems(MenuItemsServiceSJ menuItemsServiceSJ) {
        this.menuItemsServiceSJ = menuItemsServiceSJ;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetAllMenuItems getAllMenuItems = container.select(GetAllMenuItems.class).get();
        System.out.println(getAllMenuItems.menuItemsServiceSJ.getAllJDBC());
    }
}


