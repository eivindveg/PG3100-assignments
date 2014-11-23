package no.student.westerdals.vegeiv13.innlevering2.util;

import no.student.westerdals.vegeiv13.innlevering2.models.Table;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class DBHandlerTest {


    private static final String TABLE_NAME = "testTable";
    private DBHandler dbHandler;
    private Connection connection;
    private Statement statementMock;

    @Before
    public void setupConnection() throws Exception {
        final ResultSet resultMock = mock(ResultSet.class);
        connection = mock(Connection.class);
        statementMock = mock(Statement.class);
        dbHandler = new DBHandler(connection);
        verify(connection, atLeastOnce()).setAutoCommit(false);
        when(connection.createStatement()).thenReturn(statementMock);
        ResultSetMetaData metaDataMock = mock(ResultSetMetaData.class);
        when(resultMock.getMetaData()).thenReturn(metaDataMock);
        when(metaDataMock.getColumnCount()).thenReturn(2);
        when(metaDataMock.getColumnName(1)).thenReturn("test1");
        when(metaDataMock.getColumnTypeName(1)).thenReturn("INT");
        when(metaDataMock.getColumnName(2)).thenReturn("test2");
        when(metaDataMock.getColumnTypeName(2)).thenReturn("VARCHAR");
        when(statementMock.getResultSet()).thenReturn(resultMock);
        when(statementMock.execute(any(String.class))).thenReturn(true);
    }

    @Test
    public void fetchTableTest() throws Exception {
        Table table = dbHandler.fetchTable(TABLE_NAME);
        assertNotNull(table);
        verify(statementMock, atLeastOnce()).getResultSet();
    }

    @Test
    public void copyFileTest() throws Exception {
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        when(connection.prepareStatement(any())).thenReturn(preparedStatementMock);
        dbHandler.copyFile("tekstfil.txt", TABLE_NAME);
        verify(connection, atLeastOnce()).commit();
    }

}
