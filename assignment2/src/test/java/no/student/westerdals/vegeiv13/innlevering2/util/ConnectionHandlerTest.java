package no.student.westerdals.vegeiv13.innlevering2.util;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

public class ConnectionHandlerTest {

    ConnectionHandler connectionHandler;

    @Before
    public void setup() throws SQLException, ConfigurationException {
        connectionHandler = new ConnectionHandler();
    }

    @Test
    public void testGetConnection() throws Exception {
        Connection connection = connectionHandler.getConnection();
        connection.close();

    }

    @Test
    public void testMultipleConnections() throws SQLException {
        Connection connection1 = connectionHandler.getConnection();
        Connection connection2 = connectionHandler.getConnection();
        assertThat(!connection1.equals(connection2));
    }

    @After
    public void tearDown() throws Exception {
        connectionHandler.close();
    }
}
