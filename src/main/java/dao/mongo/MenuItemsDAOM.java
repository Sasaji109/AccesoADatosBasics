package dao.mongo;

import domain.model.ErrorC;
import domain.model.mongo.MenuItemMongo;
import io.vavr.control.Either;
import java.util.List;

public interface MenuItemsDAOM {
    Either<ErrorC, List<MenuItemMongo>> getAll();
    Either<ErrorC, MenuItemMongo> getMenuItem(Integer menuItemId);
    Either<ErrorC, MenuItemMongo> getMenuItemByName(String menuItemName);
}
