package domain.services.jdbcYSpring;

import dao.jdbcYSpring.MenuItemsDAOSJ;
import domain.model.springJDBC.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import java.util.List;

public class MenuItemsServiceSJ {
    private final MenuItemsDAOSJ dao;
    @Inject
    public MenuItemsServiceSJ(MenuItemsDAOSJ dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<MenuItem>> getAllJDBC() {
        return dao.getAllJDBC();
    }
    public Either<ErrorC, MenuItem> getByNameJDBC(String name) {
        return dao.getByNameJDBC(name);
    }
}
