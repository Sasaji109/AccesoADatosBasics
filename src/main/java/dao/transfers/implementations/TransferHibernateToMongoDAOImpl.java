package dao.transfers.implementations;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import common.configuration.JPAUtil;
import common.configuration.MongoDBConfig;
import dao.transfers.TransferHibernateToMongoDAO;
import domain.model.ErrorC;
import domain.model.hibernate.*;
import domain.model.mongo.*;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransferHibernateToMongoDAOImpl implements TransferHibernateToMongoDAO {

    private final JPAUtil jpaUtil;
    private final MongoDatabase mongoDatabase;

    private EntityManager em;

    @Inject
    public TransferHibernateToMongoDAOImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    @Override
    public Either<ErrorC, Integer> transferHibernateToMongo() {
        Either<ErrorC, Integer> either;
        try {
            List<MenuItem> menuItems = getAllMenuItems().getOrElse(Collections.emptyList());
            List<MenuItemMongo> menuItemMongoList = mapMenuItems(menuItems);
            saveMenuItemsToMongo(menuItemMongoList);

            List<Customers> customersList = getAllCustomersCredentials().getOrElse(Collections.emptyList());
            List<CredentialsMongo> credentialsMongoList = mapCredentials(customersList);
            saveCredentialsToMongo(credentialsMongoList);

            List<CustomersMongo> customersMongoList = mapCustomers(customersList);
            saveCustomersToMongo(customersMongoList);

            either = Either.right(customersMongoList.size());
        } catch (Exception e) {
            either = Either.left(new ErrorC(6, Constants.MAPPING_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    private List<MenuItemMongo> mapMenuItems(List<MenuItem> menuItems) {
        List<MenuItemMongo> menuItemMongoList = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {
            MenuItemMongo menuItemMongo = new MenuItemMongo();
            menuItemMongo.set_id(menuItem.getId());
            menuItemMongo.setName(menuItem.getName());
            menuItemMongo.setDescription(menuItem.getDescription());
            menuItemMongo.setPrice(menuItem.getPrice());
            menuItemMongoList.add(menuItemMongo);
        }
        return menuItemMongoList;
    }

    private List<CredentialsMongo> mapCredentials(List<Customers> customersList) {
        List<CredentialsMongo> credentialsMongoList = new ArrayList<>();

        for (Customers customer : customersList) {
            Credentials credentials = customer.getCredentials();

            if (credentials != null) {
                CredentialsMongo credentialsMongo = new CredentialsMongo();
                credentialsMongo.set_id(null);
                credentialsMongo.setUser_name(credentials.getUsername());
                credentialsMongo.setPassword(credentials.getPassword());
                credentialsMongoList.add(credentialsMongo);
            }
        }
        return credentialsMongoList;
    }

    private List<CustomersMongo> mapCustomers(List<Customers> customersList) {
        List<CustomersMongo> customersMongoList = new ArrayList<>();

        for (Customers customer : customersList) {
            CustomersMongo customersMongo = new CustomersMongo();
            customersMongo.set_id(null);
            customersMongo.setFirst_name(customer.getFirstName());
            customersMongo.setLast_name(customer.getLastName());
            customersMongo.setEmail(customer.getEmail());
            customersMongo.setPhone(customer.getPhone());
            customersMongo.setDate_of_birth(customer.getDateBirth().toString());

            List<Order> orders = getAllOrdersOrderItems().getOrElse(Collections.emptyList());
            List<OrderMongo> orderMongoList = mapOrders(orders, customer.getCustomersId());
            customersMongo.setOrders(orderMongoList);

            customersMongoList.add(customersMongo);
        }

        return customersMongoList;
    }

    private List<OrderMongo> mapOrders(List<Order> allOrders, Integer customerId) {
        List<OrderMongo> orderMongoList = new ArrayList<>();

        for (Order order : allOrders) {
            if (order.getCustomerId().equals(customerId)) {
                OrderMongo orderMongo = new OrderMongo();
                orderMongo.setOrder_date(order.getOrderDate().toString());
                orderMongo.setTable_id(order.getTableId());
                List<OrderItemMongo> orderItemMongoList = mapOrderItems(order.getOrderItemList());
                orderMongo.setOrder_items(orderItemMongoList);

                orderMongoList.add(orderMongo);
            }
        }
        return orderMongoList;
    }

    private List<OrderItemMongo> mapOrderItems(List<OrderItem> orderItems) {
        List<OrderItemMongo> orderItemMongoList = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            OrderItemMongo orderItemMongo = new OrderItemMongo();
            orderItemMongo.setMenu_item_id(orderItem.getMenuItem().getId());
            orderItemMongo.setQuantity(orderItem.getQuantity());
            orderItemMongoList.add(orderItemMongo);
        }
        return orderItemMongoList;
    }

    private void saveMenuItemsToMongo(List<MenuItemMongo> menuItemMongoList) {
        MongoCollection<Document> menuItemsCollection = mongoDatabase.getCollection("menuitems");

        for (MenuItemMongo menuItemMongo : menuItemMongoList) {
            Document document = Document.parse(new Gson().toJson(menuItemMongo));
            menuItemsCollection.insertOne(document);
        }
    }

    private void saveCredentialsToMongo(List<CredentialsMongo> credentialsMongoList) {
        MongoCollection<Document> credentialsCollection = mongoDatabase.getCollection("credentials");

        for (CredentialsMongo credentialsMongo : credentialsMongoList) {
            Document document = Document.parse(new Gson().toJson(credentialsMongo));
            credentialsCollection.insertOne(document);
        }
    }

    private void saveCustomersToMongo(List<CustomersMongo> customersMongoList) {
        MongoCollection<Document> customersCollection = mongoDatabase.getCollection("customers");

        for (CustomersMongo customersMongo : customersMongoList) {
            Document document = Document.parse(new Gson().toJson(customersMongo));
            customersCollection.insertOne(document);
        }
    }

    private Either<ErrorC, List<Customers>> getAllCustomersCredentials() {
        Either<ErrorC, List<Customers>> either;

        List<Customers> customers;
        em = jpaUtil.getEntityManager();

        try {
            customers = em.createNamedQuery( "HQL_GET_ALL_CUSTOMERS", Customers.class).getResultList();
            either = Either.right(customers);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    private Either<ErrorC, List<Order>> getAllOrdersOrderItems() {
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

    private Either<ErrorC, List<MenuItem>> getAllMenuItems() {
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
}
