package dao.hibernate;

import domain.model.ErrorC;
import domain.model.hibernate.MenuItemH;
import io.vavr.control.Either;
import java.util.List;

public interface MenuItemsDAO {
    Either<ErrorC, List<MenuItemH>> getAll();
    Either<ErrorC, MenuItemH> getByName(String name);
}
