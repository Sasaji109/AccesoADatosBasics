package domain.services.mongo;

import dao.mongo.MenuItemsDAOM;
import domain.model.ErrorC;
import domain.model.mongo.MenuItemMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class MenuItemsServiceM {
    private final MenuItemsDAOM dao;
    @Inject
    public MenuItemsServiceM(MenuItemsDAOM dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<MenuItemMongo>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, MenuItemMongo> getMenuItem(Integer menuItemId) {
        return dao.getMenuItem(menuItemId);
    }

    public Either<ErrorC, MenuItemMongo> getMenuItemByName(String menuItemName) {
        return dao.getMenuItemByName(menuItemName);
    }
}
