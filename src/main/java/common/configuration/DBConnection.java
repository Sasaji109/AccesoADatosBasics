package common.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.uitls.Constants;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class DBConnection {

    private final ConfigurationJDBC config;

    private DataSource hikariDataSource = null;

    @Inject
    public DBConnection(ConfigurationJDBC config) {
        this.config = config;
        hikariDataSource = getDataSourceHikari();
    }

    private DataSource getDataSourceHikari() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty(Constants.URL_DB));
        hikariConfig.setUsername(config.getProperty(Constants.USER_NAME));
        hikariConfig.setPassword(config.getProperty(Constants.PASSWORD));
        hikariConfig.setDriverClassName(config.getProperty(Constants.DRIVER));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty(Constants.CACHE_PREP_STMTS, true);
        hikariConfig.addDataSourceProperty(Constants.PREP_STMT_CACHE_SIZE, 250);
        hikariConfig.addDataSourceProperty(Constants.PREP_STMT_CACHE_SQL_LIMIT, 2048);

        return new HikariDataSource(hikariConfig);
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }

    public Connection getConnection() {
        Connection con=null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}
