package dao.hibernate.implementations;

import common.uitls.Constants;
import common.configuration.JPAUtil;
import dao.hibernate.CustomersDAO;
import domain.model.ErrorC;
import domain.model.hibernate.CredentialsH;
import domain.model.hibernate.CustomersH;
import domain.model.hibernate.OrderH;
import domain.xml.OrderXML;
import domain.xml.OrdersXML;
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
    public Either<ErrorC, List<CustomersH>> getAll() {
        Either<ErrorC, List<CustomersH>> either;

        List<CustomersH> customers;
        em = jpaUtil.getEntityManager();

        try {
            customers = em.createNamedQuery( "HQL_GET_ALL_CUSTOMERS", CustomersH.class).getResultList();
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
    public Either<ErrorC, CustomersH> get(int id) {
        Either<ErrorC, CustomersH> either;
        em = jpaUtil.getEntityManager();

        try {
            CustomersH customer = em.find(CustomersH.class,id);
            either = Either.right(customer);
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        } finally {
            em.close();
        }
        return either;
    }

    @Override
    public Either<ErrorC, Integer> add(CustomersH customer) {
        Either<ErrorC, Integer> either;
        em = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        try {
            entityTransaction.begin();

            CredentialsH credentialsH = customer.getCredentialsH();
            em.persist(credentialsH);

            CustomersH customer1 = new CustomersH(credentialsH.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateBirth());
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
    public Either<ErrorC, Integer> update(CustomersH customer) {
        Either<ErrorC, Integer> either;
        CredentialsH credential = customer.getCredentialsH();

        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            CustomersH customer1 = new CustomersH(customer.getCustomersId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateBirth());
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
    public Either<ErrorC, Integer> delete(CustomersH customer, boolean orders) {
        Either<ErrorC, Integer> either;
        CredentialsH credential = customer.getCredentialsH();

        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            if (orders) {
                List<OrderXML> ordersList = em.createQuery("SELECT o FROM OrderXML o WHERE o.customerId = :customerId", OrderXML.class)
                        .setParameter("customerId", customer.getCustomersId()).getResultList();

                backupOrdersToXml(ordersList, customer.getFirstName());

                for (OrderXML order : ordersList) {
                    em.remove(em.merge(order));
                }
            }

            CustomersH customer1 = new CustomersH(customer.getCustomersId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateBirth());
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

    private boolean backupOrdersToXml(List<OrderXML> ordersList, String customerName) throws Exception {
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
