package dao.transfers.implementations;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.uitls.Constants;
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
            List<MenuItemH> menuItemHS = getAllMenuItems().getOrElse(Collections.emptyList());
            List<MenuItemMongo> menuItemMongoList = mapMenuItems(menuItemHS);
            saveMenuItemsToMongo(menuItemMongoList);

            List<CustomersH> customersHList = getAllCustomersCredentials().getOrElse(Collections.emptyList());
            List<CredentialsMongo> credentialsMongoList = mapCredentials(customersHList);
            saveCredentialsToMongo(credentialsMongoList);

            List<CustomersMongo> customersMongoList = mapCustomers(customersHList);
            saveCustomersToMongo(customersMongoList);

            either = Either.right(customersMongoList.size());
        } catch (Exception e) {
            either = Either.left(new ErrorC(6, Constants.MAPPING_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    private List<MenuItemMongo> mapMenuItems(List<MenuItemH> menuItemHS) {
        List<MenuItemMongo> menuItemMongoList = new ArrayList<>();

        for (MenuItemH menuItemH : menuItemHS) {
            MenuItemMongo menuItemMongo = new MenuItemMongo();
            menuItemMongo.set_id(menuItemH.getId());
            menuItemMongo.setName(menuItemH.getName());
            menuItemMongo.setDescription(menuItemH.getDescription());
            menuItemMongo.setPrice(menuItemH.getPrice());
            menuItemMongoList.add(menuItemMongo);
        }
        return menuItemMongoList;
    }

    private List<CredentialsMongo> mapCredentials(List<CustomersH> customersHList) {
        List<CredentialsMongo> credentialsMongoList = new ArrayList<>();

        for (CustomersH customer : customersHList) {
            CredentialsH credentialsH = customer.getCredentialsH();

            if (credentialsH != null) {
                CredentialsMongo credentialsMongo = new CredentialsMongo();
                credentialsMongo.set_id(null);
                credentialsMongo.setUser_name(credentialsH.getUsername());
                credentialsMongo.setPassword(credentialsH.getPassword());
                credentialsMongoList.add(credentialsMongo);
            }
        }
        return credentialsMongoList;
    }

    private List<CustomersMongo> mapCustomers(List<CustomersH> customersHList) {
        List<CustomersMongo> customersMongoList = new ArrayList<>();

        for (CustomersH customer : customersHList) {
            CustomersMongo customersMongo = new CustomersMongo();
            customersMongo.set_id(null);
            customersMongo.setFirst_name(customer.getFirstName());
            customersMongo.setLast_name(customer.getLastName());
            customersMongo.setEmail(customer.getEmail());
            customersMongo.setPhone(customer.getPhone());
            customersMongo.setDate_of_birth(customer.getDateBirth().toString());

            List<OrderH> orderHS = getAllOrdersOrderItems().getOrElse(Collections.emptyList());
            List<OrderMongo> orderMongoList = mapOrders(orderHS, customer.getCustomersId());
            customersMongo.setOrders(orderMongoList);

            customersMongoList.add(customersMongo);
        }

        return customersMongoList;
    }

    private List<OrderMongo> mapOrders(List<OrderH> allOrderHS, Integer customerId) {
        List<OrderMongo> orderMongoList = new ArrayList<>();

        for (OrderH orderH : allOrderHS) {
            if (orderH.getCustomerId().equals(customerId)) {
                OrderMongo orderMongo = new OrderMongo();
                orderMongo.setOrder_date(orderH.getOrderDate().toString());
                orderMongo.setTable_id(orderH.getTableId());
                List<OrderItemMongo> orderItemMongoList = mapOrderItems(orderH.getOrderItemHList());
                orderMongo.setOrder_items(orderItemMongoList);

                orderMongoList.add(orderMongo);
            }
        }
        return orderMongoList;
    }

    private List<OrderItemMongo> mapOrderItems(List<OrderItemH> orderItemHS) {
        List<OrderItemMongo> orderItemMongoList = new ArrayList<>();

        for (OrderItemH orderItemH : orderItemHS) {
            OrderItemMongo orderItemMongo = new OrderItemMongo();
            orderItemMongo.setMenu_item_id(orderItemH.getMenuItemH().getId());
            orderItemMongo.setQuantity(orderItemH.getQuantity());
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

    private Either<ErrorC, List<CustomersH>> getAllCustomersCredentials() {
        Either<ErrorC, List<CustomersH>> either;

        List<CustomersH> customers;
        em = jpaUtil.getEntityManager();

        try {
            customers = em.createNamedQuery( "HQL_GET_ALL_CUSTOMERS", CustomersH.class).getResultList();
            either = Either.right(customers);
        }
        catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    private Either<ErrorC, List<OrderH>> getAllOrdersOrderItems() {
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

    private Either<ErrorC, List<MenuItemH>> getAllMenuItems() {
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
}
