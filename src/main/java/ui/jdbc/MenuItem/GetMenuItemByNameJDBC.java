package ui.jdbc.MenuItem;

import domain.services.jdbcYSpring.MenuItemsServiceSJ;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class GetMenuItemByNameJDBC {

    private final MenuItemsServiceSJ menuItemsServiceSJ;

    @Inject
    public GetMenuItemByNameJDBC(MenuItemsServiceSJ menuItemsServiceSJ) {
        this.menuItemsServiceSJ = menuItemsServiceSJ;
    }

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        GetMenuItemByNameJDBC getMenuItemByNameJDBC = container.select(GetMenuItemByNameJDBC.class).get();
        System.out.println(getMenuItemByNameJDBC.menuItemsServiceSJ.getByNameJDBC("Caesar Salad"));
    }
}


