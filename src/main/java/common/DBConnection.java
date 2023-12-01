package common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@Singleton
public class DBConnection {

    private final Configuration config;
    private final DataSource hikariDataSource;

    @Inject
    public DBConnection(Configuration config) {
        this.config = config;
        this.hikariDataSource = getHikariDataSource();
    }

    private DataSource getHikariDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPathDB());
        hikariConfig.setUsername(config.getUserDB());
        hikariConfig.setPassword(config.getPassDB());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
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

    public DataSource getDataSource() {
        return hikariDataSource;
    }

}
