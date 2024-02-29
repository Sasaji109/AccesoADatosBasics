package dao.hibernate.implementations;

import common.uitls.Constants;
import common.configuration.JPAUtil;
import dao.hibernate.OrdersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.OrderH;
import domain.model.hibernate.OrderItemH;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public OrdersDAOImpl(JPAUtil jpaUtil){
        this.jpaUtil =jpaUtil;
    }

    @Override
    public Either<ErrorC, List<OrderH>> getAll() {
        Either<ErrorC, List<OrderH>> either;
        List<OrderH> orderHS;
        em = jpaUtil.getEntityManager();

        try {
            orderHS = em.createQuery("FROM OrderH", OrderH.class).getResultList();
            either = Either.right(orderHS);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, OrderH> get(int orderId) {
        Either<ErrorC, OrderH> either;

        OrderH orderH;
        em = jpaUtil.getEntityManager();

        try {
            orderH = em.find(OrderH.class, orderId);
            either = Either.right(orderH);
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> add(OrderH orderH) {
        Either<ErrorC, Integer> either;

        orderH.setOrderDate(LocalDateTime.now());
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            OrderH orderH1 = new OrderH(orderH.getOrderId(), orderH.getOrderDate(), orderH.getCustomerId(), orderH.getTableId());
            em.persist(orderH1);

            Integer orderId = orderH1.getOrderId();
            List<OrderItemH> orderItemHList = orderH.getOrderItemHList();
            if (orderItemHList != null && !orderItemHList.isEmpty()) {
                for (OrderItemH orderItemH : orderItemHList) {
                    OrderItemH orderItemH1 = new OrderItemH(
                            orderItemH.getId(),
                            orderId,
                            orderItemH.getMenuItemH(),
                            orderItemH.getQuantity()
                    );
                    em.persist(orderItemH1);
                }
            }

            tx.commit();

            int rowsAffected = 1;
            either = Either.right(rowsAffected);
        }
        catch (PersistenceException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> update(OrderH orderH) {
        Either<ErrorC, Integer> either;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(orderH);

            List<OrderItemH> orderItemHList = orderH.getOrderItemHList();
            if (orderItemHList != null && !orderItemHList.isEmpty()) {
                for (OrderItemH orderItemH : orderItemHList) {
                    OrderItemH orderItemH1 = new OrderItemH(
                            orderItemH.getId(),
                            orderItemH.getOrderId(),
                            orderItemH.getMenuItemH(),
                            orderItemH.getQuantity()
                    );
                    em.merge(orderItemH1);
                }
            }

            tx.commit();

            int rowsAffected = 1;
            either = Either.right(rowsAffected);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> delete(OrderH orderH) {
        Either<ErrorC, Integer> either;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.remove(em.merge(orderH));
            tx.commit();

            int rowsAffected = 1;
            either = Either.right(rowsAffected);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }

        return either;
    }
}
