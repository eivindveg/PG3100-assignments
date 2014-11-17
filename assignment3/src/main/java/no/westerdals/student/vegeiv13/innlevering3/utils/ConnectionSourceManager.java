package no.westerdals.student.vegeiv13.innlevering3.utils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import org.apache.commons.configuration.Configuration;

import java.sql.SQLException;

public class ConnectionSourceManager {

    private ConnectionSourceManager() {

    }

    public static JdbcConnectionSource mapConfigurationToJdbcConnectionSource(Configuration configuration) throws SQLException {
        final String schema = configuration.getString("database.schema", "ServletsAssignment");
        String urlPrefix = "jdbc:";
        final String dbType = configuration.getString("database.type", "unsupported");
        switch (dbType) {
            case "h2":
                urlPrefix += "h2:";
                break;
            case "mysql":
                urlPrefix += "mysql://";
                break;
            default:
                throw new UnsupportedOperationException("Unsupported JDBC Database");
        }
        String url = urlPrefix + "" + configuration.getString("database.url", "localhost");
        if(dbType.equals("mysql") && !url.endsWith("/")) {
            url += "/";
        }
        url += schema;
        final String user = configuration.getString("database.user", "root");
        final String password = configuration.getString("database.password", "");

        return new JdbcConnectionSource(url, user, password);
    }
}
