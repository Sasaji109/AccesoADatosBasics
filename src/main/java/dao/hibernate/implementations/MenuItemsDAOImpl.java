package dao.hibernate.implementations;

import common.uitls.Constants;
import common.configuration.JPAUtil;
import dao.hibernate.MenuItemsDAO;
import domain.model.ErrorC;
import domain.model.hibernate.MenuItemH;
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
    public Either<ErrorC, List<MenuItemH>> getAll() {
        Either<ErrorC, List<MenuItemH>> either;

        List<MenuItemH> menuItemHS;
        em = jpaUtil.getEntityManager();

        try {
            menuItemHS = em.createQuery("FROM MenuItemH", MenuItemH.class).getResultList();
            either = Either.right(menuItemHS);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, MenuItemH> getByName(String name) {
        Either<ErrorC, MenuItemH> either;

        MenuItemH menuItemH;
        em = jpaUtil.getEntityManager();

        try {
            menuItemH = em.createQuery("FROM MenuItemH WHERE name = :name", MenuItemH.class)
                    .setParameter("name", name)
                    .getSingleResult();
            either = Either.right(menuItemH);
        } catch (NoResultException e) {
            either = Either.left(new ErrorC(404, Constants.MENU_ITEM_NOT_FOUND, LocalDate.now()));
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
