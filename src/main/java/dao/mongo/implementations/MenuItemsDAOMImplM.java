package dao.mongo.implementations;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import common.uitls.Constants;
import common.configuration.MongoDBConfig;
import dao.mongo.MenuItemsDAOM;
import domain.model.ErrorC;
import domain.model.mongo.MenuItemMongo;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.bson.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MenuItemsDAOMImplM implements MenuItemsDAOM {

    private final MongoDatabase mongoDatabase;

    @Inject
    public MenuItemsDAOMImplM() {
        this.mongoDatabase = MongoDBConfig.getMongoDatabase();
    }

    @Override
    public Either<ErrorC, List<MenuItemMongo>> getAll() {
        Either<ErrorC, List<MenuItemMongo>> either;

        try {
            MongoCollection<Document> est = mongoDatabase.getCollection("menuitems");
            List<MenuItemMongo> menuItems = new ArrayList<>();
            List<Document> documents = est.find().into(new ArrayList<>());

            for (Document supplier : documents) {
                menuItems.add(new Gson().fromJson(supplier.toJson(), MenuItemMongo.class));
            }
            either = Either.right(menuItems);

        } catch(Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, MenuItemMongo> getMenuItem(Integer menuItemId) {
        Either<ErrorC, MenuItemMongo> either;

        try {
            MongoCollection<Document> menuItemsCollection = mongoDatabase.getCollection("menuitems");
            Document menuItemDocument = menuItemsCollection.find(Filters.eq("_id", menuItemId)).first();

            if (menuItemDocument != null) {
                MenuItemMongo menuItem = new Gson().fromJson(menuItemDocument.toJson(), MenuItemMongo.class);
                either = Either.right(menuItem);
            } else {
                either = Either.left(new ErrorC(404, Constants.MENU_ITEM_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }

    @Override
    public Either<ErrorC, MenuItemMongo> getMenuItemByName(String menuItemName) {
        Either<ErrorC, MenuItemMongo> either;

        try {
            MongoCollection<Document> menuItemsCollection = mongoDatabase.getCollection("menuitems");
            Document menuItemDocument = menuItemsCollection.find(Filters.eq("name", menuItemName)).first();

            if (menuItemDocument != null) {
                MenuItemMongo menuItem = new Gson().fromJson(menuItemDocument.toJson(), MenuItemMongo.class);
                either = Either.right(menuItem);
            } else {
                either = Either.left(new ErrorC(404, Constants.MENU_ITEM_NOT_FOUND, LocalDate.now()));
            }
        } catch (Exception e) {
            either = Either.left(new ErrorC(5, Constants.MONGO_ERROR + e.getMessage(), LocalDate.now()));
        }

        return either;
    }
}
