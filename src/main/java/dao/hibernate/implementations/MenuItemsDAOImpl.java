package dao.hibernate.implementations;

import common.Constants;
import common.configuration.JPAUtil;
import dao.hibernate.MenuItemsDAO;
import domain.model.ErrorC;
import domain.model.hibernate.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

public class MenuItemsDAOImpl implements MenuItemsDAO {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public MenuItemsDAOImpl(JPAUtil jpaUtil){
        this.jpaUtil =jpaUtil;
    }

    @Override
    public Either<ErrorC, List<MenuItem>> getAll() {
        Either<ErrorC, List<MenuItem>> either;

        List<MenuItem> menuItems;
        em = jpaUtil.getEntityManager();

        try {
            menuItems = em.createQuery("FROM MenuItem", MenuItem.class).getResultList();
            either = Either.right(menuItems);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, MenuItem> getByName(String name) {
        Either<ErrorC, MenuItem> either;

        MenuItem menuItem;
        em = jpaUtil.getEntityManager();

        try {
            menuItem = em.createQuery("FROM MenuItem WHERE name = :name", MenuItem.class)
                    .setParameter("name", name)
                    .getSingleResult();
            either = Either.right(menuItem);
        } catch (NoResultException e) {
            either = Either.left(new ErrorC(404, Constants.MENU_ITEM_NOT_FOUND, LocalDate.now()));
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
