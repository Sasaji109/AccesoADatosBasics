package common.uitls;

public class Constants {

    //ConfigurationJDBC
    public static final String PATH = "src/main/java/common/configuration/mysql-properties.xml";

    //DBConnection
    public static final String URL_DB = "urlDB";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String DRIVER = "driver";
    public static final String CACHE_PREP_STMTS = "cachePrepStmts";
    public static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    public static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";


    //Mongo Configuration
    public static final String DATABASE_HOST = "mongodb://root:root@localhost:";
    public static final String DATABASE_PORT = "27017";
    public static final String DATABASE_NAME = "restaurantDB";


    //Data Transfer
    public static final String MAPPING_ERROR = "Error on mapping the classes from SQL";


    //Alerts and Errors
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CREDENTIALS_NOT_FOUND = "CredentialsH not found";
    public static final String MENU_ITEM_NOT_FOUND = "MenuItemH not found";
    public static final String SQL_ERROR = "SQL error: ";
    public static final String MONGO_ERROR = "Mongo error: ";
    public static final String ERROR_ON_LOADING_CUSTOMERS = "Error on loading customers";
    public static final String ERROR_ON_ADDING_THE_CUSTOMER = "Error on adding the customer";
    public static final String ERROR_ON_LOADING_CREDENTIALS = "Error on loading credentials";
    public static final String CREDENTIAL_NOT_FOUND = "Credential not found";
    public static final String ERROR_ON_CREATING_CREDENTIALS_FOR_THE_CUSTOMER = "Error on creating credentials for the customer";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String ERROR_ON_LOADING_ORDERS = "Error on loading orders";
    public static final String ERROR_ON_ADDING_THE_ORDER = "Error on adding the order";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String ERROR_ON_ADDING_THE_ORDER_ITEM = "Error on adding the order item";
    public static final String ORDER_ITEM_NOT_FOUND = "Order item not found";
    public static final String ERROR_ON_RETRIEVING_THE_ORDER_ITEM = "Error on retrieving the order item";
    public static final String ERROR_ON_LOADING_MENU_ITEMS = "Error on loading menuItems";
    public static final String MENU_ITEM_NOT_FOUND_FOR_NAME = "MenuItem not found for name: ";
    public static final String ERROR_ON_LOADING_TABLES = "Error on loading tables";

    private Constants(){}
}
