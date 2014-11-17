package no.student.westerdals.vegeiv13.innlevering2.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionHandler implements AutoCloseable {

    private Connection connection;

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public ConnectionHandler() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        Configuration configuration;
        try {
            configuration = ConfigurationHandler.getInstance().getConfiguration();
        } catch (ConfigurationException e) {
            configuration = new XMLConfiguration();
        }

        dataSource.setServerName(configuration.getString("database.host", "localhost"));
        dataSource.setDatabaseName(configuration.getString("database.schema", ""));
        dataSource.setPort(configuration.getInt("database.port", 3306));
        dataSource.setUser(configuration.getString("database.user", "root"));
        dataSource.setPassword(configuration.getString("database.password", ""));

        connection = dataSource.getConnection();
    }

    /**
     * Closing the ConnectionHandler should only happen during the end of the application's lifetime.
     * Otherwise, all connections should be closed individually by the resource that requested them. However, any
     * remaining connections are closed by called closing on the ConnectionHandler.
     */
    @Override
    public void close() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }
}
