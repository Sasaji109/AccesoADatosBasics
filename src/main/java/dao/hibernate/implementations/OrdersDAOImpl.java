package dao.hibernate.implementations;

import common.Constants;
import common.configuration.JPAUtil;
import dao.hibernate.OrdersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.Order;
import domain.model.hibernate.OrderItem;
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
    public Either<ErrorC, List<Order>> getAll() {
        Either<ErrorC, List<Order>> either;
        List<Order> orders;
        em = jpaUtil.getEntityManager();

        try {
            orders = em.createQuery("FROM Order", Order.class).getResultList();
            either = Either.right(orders);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Order> get(int orderId) {
        Either<ErrorC, Order> either;

        Order order;
        em = jpaUtil.getEntityManager();

        try {
            order = em.find(Order.class, orderId);
            either = Either.right(order);
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> add(Order order) {
        Either<ErrorC, Integer> either;

        order.setOrderDate(LocalDateTime.now());
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            Order order1 = new Order(order.getOrderId(), order.getOrderDate(), order.getCustomerId(), order.getTableId());
            em.persist(order1);

            Integer orderId = order1.getOrderId();
            List<OrderItem> orderItemList = order.getOrderItemList();
            if (orderItemList != null && !orderItemList.isEmpty()) {
                for (OrderItem orderItem : orderItemList) {
                    OrderItem orderItem1 = new OrderItem(
                            orderItem.getId(),
                            orderId,
                            orderItem.getMenuItem(),
                            orderItem.getQuantity()
                    );
                    em.persist(orderItem1);
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
    public Either<ErrorC, Integer> update(Order order) {
        Either<ErrorC, Integer> either;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(order);
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
    public Either<ErrorC, Integer> delete(Order order) {
        Either<ErrorC, Integer> either;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.remove(em.merge(order));
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
