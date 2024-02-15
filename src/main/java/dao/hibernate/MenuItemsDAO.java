package dao.hibernate;

import domain.model.ErrorC;
import domain.model.hibernate.MenuItem;
import io.vavr.control.Either;
import java.util.List;

public interface MenuItemsDAO {
    Either<ErrorC, List<MenuItem>> getAll();
    Either<ErrorC, MenuItem> getByName(String name);
}
