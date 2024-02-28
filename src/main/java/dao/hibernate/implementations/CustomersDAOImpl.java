package dao.hibernate.implementations;

import common.Constants;
import common.configuration.JPAUtil;
import dao.hibernate.CustomersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.Credentials;
import domain.model.hibernate.Customers;
import domain.model.hibernate.Order;
import domain.model.hibernate.OrdersXML;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

public class CustomersDAOImpl implements CustomersDAO {

    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public CustomersDAOImpl(JPAUtil jpaUtil){
        this.jpaUtil =jpaUtil;
    }

    @Override
    public Either<ErrorC, List<Customers>> getAll() {
        Either<ErrorC, List<Customers>> either;

        List<Customers> customers;
        em = jpaUtil.getEntityManager();

        try {
            customers = em.createNamedQuery( "HQL_GET_ALL_CUSTOMERS", Customers.class).getResultList();
            either = Either.right(customers);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }
        return either;
    }

    @Override
    public Either<ErrorC, Customers> get(int id) {
        Either<ErrorC, Customers> either;
        em = jpaUtil.getEntityManager();

        try {
            Customers customer = em.find(Customers.class,id);
            either = Either.right(customer);
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> add(Customers customer) {
        Either<ErrorC, Integer> either;
        em = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        try {
            entityTransaction.begin();

            Credentials credentials = customer.getCredentials();
            em.persist(credentials);

            Customers customer1 = new Customers(credentials.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateBirth());
            em.persist(customer1);
            entityTransaction.commit();

            int rowsAffected = 1;
            either = Either.right(rowsAffected);
        }
        catch (PersistenceException e) {
            if (entityTransaction != null && entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }

        return either;
    }

    @Override
    public Either<ErrorC, Integer> update(Customers customer) {
        Either<ErrorC, Integer> either;
        Credentials credential = customer.getCredentials();

        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Customers customer1 = new Customers(customer.getCustomersId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateBirth());
            em.merge(customer1);
            em.merge(credential);
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
    public Either<ErrorC, Integer> delete(Customers customer, boolean orders) {
        Either<ErrorC, Integer> either;
        Credentials credential = customer.getCredentials();

        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            if (orders) {
                List<Order> ordersList = em.createQuery("SELECT o FROM Order o WHERE o.customerId = :customerId", Order.class)
                        .setParameter("customerId", customer.getCustomersId()).getResultList();

                backupOrdersToXml(ordersList, customer.getFirstName());

                for (Order order : ordersList) {
                    em.remove(em.merge(order));
                }
            }

            Customers customer1 = new Customers(customer.getCustomersId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateBirth());
            em.remove(em.merge(customer1));
            em.remove(em.merge(credential));
            tx.commit();

            int rowsAffected = 1;
            either = Either.right(rowsAffected);
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }
        return either;
    }

    private boolean backupOrdersToXml(List<Order> ordersList, String customerName) throws Exception {
        String fileName = customerName + "_orders_backup.xml";
        File backupFile = new File(fileName);

        JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        OrdersXML orders = new OrdersXML();
        orders.setOrdersList(ordersList);

        try (OutputStream outputStream = new FileOutputStream(backupFile)) {
            marshaller.marshal(orders, outputStream);
        }

        return true;
    }
}
