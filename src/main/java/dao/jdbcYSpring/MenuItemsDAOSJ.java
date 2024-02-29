package dao.jdbcYSpring;

import domain.model.springJDBC.MenuItem;
import io.vavr.control.Either;
import domain.model.ErrorC;
import java.util.List;

public interface MenuItemsDAOSJ {
    Either<ErrorC, List<MenuItem>> getAllJDBC();
    Either<ErrorC, MenuItem> getByNameJDBC(String name);
}
