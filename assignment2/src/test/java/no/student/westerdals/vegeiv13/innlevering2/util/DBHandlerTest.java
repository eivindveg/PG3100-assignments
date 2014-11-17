package no.student.westerdals.vegeiv13.innlevering2.util;

import no.student.westerdals.vegeiv13.innlevering2.models.Table;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

public class DBHandlerTest {


    private static final String TABLE_NAME = "testTable";
    private DBHandler dbHandler;
    private Connection connection;

    @Before
    public void setupConnection() throws Exception {
        connection = new ConnectionHandler().getConnection();
        dbHandler = new DBHandler(connection);
    }

    @Test
    public void fetchTableTest() throws Exception {
        Table table = dbHandler.fetchTable(TABLE_NAME);
        assertThat(table != null);
    }

    @Test
    public void copyFileTest() throws Exception {
        dbHandler.copyFile("tekstfil.txt", TABLE_NAME);
    }

    @Test(expected = SQLException.class)
    public void testConnectionClosedFailure() throws SQLException, IOException {
        connection.close();
        dbHandler.fetchTable(TABLE_NAME);
        dbHandler.copyFile("tekstfil.txt", TABLE_NAME);
    }

}
