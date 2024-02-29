package common.uitls;

public class SQLQueries {

    private SQLQueries() {}

    public static final String INSERT_FACTION = "insert INTO faction (fname, contact, planet, number_controlled_systems, date_last_purchase) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_WEAPON = "insert INTO weapons (wname, wprice) VALUES (?, ?)";
    public static final String INSERT_WEAPON_FACTION = "insert INTO weapons_factions (name_faction, id_weapon) VALUES (?, ?)";


    public static final String SELECT_CREDENTIALS = "select * from credentials";
    public static final String SELECT_CREDENTIAL_BY_ID = "select * from credentials where customer_id= ?";
    public static final String INSERT_CREDENTIAL = "insert INTO credentials (user_name, password) VALUES (?, ?)";
    public static final String DELETE_CREDENTIAL = "delete from credentials where customer_id = ?";



    public static final String SELECT_CUSTOMERS = "select * FROM customers";
    public static final String SELECT_CUSTOMER_BY_ID = "select * from customers where id= ?";
    public static final String INSERT_CUSTOMER = "insert INTO customers (id, first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_CUSTOMER = "update customers " +
            "SET first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ? WHERE id = ?";
    public static final String DELETE_CUSTOMER = "delete from customers where id = ?";



    public static final String SELECT_ORDERS = "SELECT * from orders";
    public static final String SELECT_ORDER_BY_ID = "select * from orders where order_id= ?";
    public static final String INSERT_ORDER = "insert INTO orders (order_id, order_date, customer_id, table_id) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_ORDER = "update orders SET order_date = ?, customer_id = ?, table_id = ? WHERE order_id = ?";
    public static final String DELETE_ORDER = "delete from orders where order_id = ?";
    public static final String DELETE_ORDERS_BY_CUSTOMER = "delete from orders WHERE customer_Id = ?";


    public static final String SELECT_ORDERITEMS = "SELECT oi.order_item_id, oi.order_id, oi.menu_item_id, oi.quantity, " +
                    "mi.menu_item_id, mi.name, mi.description, mi.price FROM order_items AS oi " +
                    "INNER JOIN menu_items AS mi ON oi.order_item_id = mi.menu_item_id";
    public static final String SELECT_ORDERITEM_BY_ID =
            "SELECT oi.order_item_id, oi.order_id, oi.menu_item_id, oi.quantity, " +
                    "mi.menu_item_id, mi.name, mi.description, mi.price " +
                    "FROM order_items AS oi " +
                    "INNER JOIN menu_items AS mi ON oi.menu_item_id = mi.menu_item_id " +
                    "WHERE oi.order_item_id = ?";

    public static final String INSERT_ORDERITEM = "insert INTO order_items (order_item_id, order_id, menu_item_id, quantity) VALUES (?, ?, ?, ?)";
    public static final String DELETE_ORDERITEM = "delete from order_items WHERE order_item_id = ?";
    public static final String DELETE_ORDER_ITEMS_BY_ORDERS = "delete from order_items WHERE order_id = ?";
    public static final String DELETE_ORDER_ITEMS_BY_CUSTOMER = "delete from order_items WHERE order_id IN (SELECT order_id FROM orders WHERE customer_id = ?)";


    public static final String SELECT_MENUITEMS = "select * from menu_items";
    public static final String SELECT_MENUITEM_BY_NAME = "SELECT * FROM menu_items WHERE name = ?";
}
