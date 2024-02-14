package common;

public class Constants {

    //Mongo Configuration
    public static final String DATABASE_HOST = "mongodb://root:root@localhost:";
    public static final String DATABASE_PORT = "27017";
    public static final String DATABASE_NAME = "restaurantDB";


    //Data Transfer
    public static final String MAPPING_ERROR = "Error on mapping the classes from SQL";


    //Customers Alerts
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";


    //Credentials Alerts
    public static final String CREDENTIALS_NOT_FOUND = "Credentials not found";


    //MenuItems Alerts
    public static final String MENU_ITEM_NOT_FOUND = "MenuItem not found";


    //Other Errors
    public static final String SQL_ERROR = "SQL error: ";
    public static final String MONGO_ERROR = "Mongo error: ";

    private Constants(){}
}
