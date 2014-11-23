package no.student.westerdals.vegeiv13.innlevering2.util;

import no.student.westerdals.vegeiv13.innlevering2.models.Table;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DBHandlerTest {


    private static final String TABLE_NAME = "testTable";
    private DBHandler dbHandler;
    private Connection connection;

    @Before
    public void setupConnection() throws Exception {
        connection = mock(Connection.class);
        dbHandler = new DBHandler(connection);
        verify(connection, atLeastOnce()).setAutoCommit(false);
    }

    @Test
    public void fetchTableTest() throws Exception {
        Table table = dbHandler.fetchTable(TABLE_NAME);
        assertNotNull(table);
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
