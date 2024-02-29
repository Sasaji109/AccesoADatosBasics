package domain.services.hibernate;

import dao.hibernate.MenuItemsDAO;
import domain.model.ErrorC;
import domain.model.hibernate.MenuItemH;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import java.util.List;

public class MenuItemsService {
    private final MenuItemsDAO dao;
    @Inject
    public MenuItemsService(MenuItemsDAO dao) {
        this.dao = dao;
    }
    public Either<ErrorC, List<MenuItemH>> getAll() {
        return dao.getAll();
    }
    public Either<ErrorC, MenuItemH> getByName(String name) {
        return dao.getByName(name);
    }
}
