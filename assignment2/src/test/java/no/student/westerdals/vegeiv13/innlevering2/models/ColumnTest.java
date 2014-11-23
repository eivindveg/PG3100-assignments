package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColumnTest {

    private Column column;

    @Before
    public void setupColumn() {
        column = new Column(DataType.BIGINT, 0, 11, "id");
    }

    @Test
    public void testColumnSetup() {
        Column column = new Column(DataType.BIGINT, 0, 11, "id");
        assertEquals(DataType.BIGINT, column.getDataType());
        assertEquals(column.getIndex(), 0);
        assertEquals(column.getName(), "id");
    }

    @Test
    public void testPrimaryKey() {
        assertTrue(!column.isPrimaryKey());
        column.setPrimaryKey(true);
        assertTrue(column.isPrimaryKey());
        column.setPrimaryKey(false);
        assertTrue(!column.isPrimaryKey());
    }

    @Test
    public void testToString() {
        String expected = "Column{name='id', index=0, dataType=BIGINT, width=11}";
        String actual = column.toString();
        assertEquals(expected, actual);
    }
}
