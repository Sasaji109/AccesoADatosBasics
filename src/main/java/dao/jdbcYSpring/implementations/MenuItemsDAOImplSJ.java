package dao.jdbcYSpring.implementations;

import common.configuration.DBConnection;
import common.uitls.Constants;
import common.uitls.SQLQueries;
import dao.jdbcYSpring.MenuItemsDAOSJ;
import domain.model.springJDBC.MenuItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.model.ErrorC;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MenuItemsDAOImplSJ implements MenuItemsDAOSJ {

    private final DBConnection db;

    @Inject
    public MenuItemsDAOImplSJ(DBConnection db) {
        this.db = db;
    }

    @Override
    public Either<ErrorC, List<MenuItem>> getAllJDBC() {
        Either<ErrorC, List<MenuItem>> either;

        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_MENUITEMS);

            while (rs.next()) {
                int id = rs.getInt("menu_item_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Double price = rs.getDouble("price");
                MenuItem menuItem = new MenuItem(id,name,description,price);
                menuItems.add(menuItem);
            }
            either = Either.right(menuItems);
        } catch (SQLException ex) {
            either = Either.left(new ErrorC(3, Constants.ERROR_ON_LOADING_MENU_ITEMS, LocalDate.now()));
        }
        return either;
    }

    @Override
    public Either<ErrorC, MenuItem> getByNameJDBC(String name) {
        Either<ErrorC, MenuItem> either;
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement(SQLQueries.SELECT_MENUITEM_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MenuItem menuItem = new MenuItem(
                            resultSet.getInt("menu_item_id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price")
                    );
                    either = Either.right(menuItem);
                } else {
                    either = Either.left(new ErrorC(4, Constants.MENU_ITEM_NOT_FOUND_FOR_NAME + name, LocalDate.now()));
                }
            }

        } catch (SQLException e) {
            either = Either.left(new ErrorC(7, Constants.SQL_ERROR + e.getMessage(), LocalDate.now()));
        }
        return either;
    }
}
