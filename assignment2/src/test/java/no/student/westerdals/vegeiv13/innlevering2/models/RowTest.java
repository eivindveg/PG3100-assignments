package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class RowTest {

    Row row;
    Column column;

    @Before
    public void setup() {
        row = new Row();
        column = new Column(DataType.BIGINT, 0, 11, "id");
        row.put(column, 8L);
        row.put(new Column(DataType.VARCHAR, 1, 10, "test"), "testValue");
    }

    @Test
    public void testToInsertStatement() {
        Object[] data = new Object[row.size()];
        String expected = "INSERT INTO TestTable (id, test) VALUES (?, ?);";
        String actual = row.toInsertStatement("TestTable", data);
        for(Object o: data) {
            assertNotNull(o);
        }
        assertEquals(expected, actual);

    }

    @Test
    public void testGetValue() throws Exception {
        Object o = row.get(column);
        assertEquals(o.toString(), String.valueOf(8L));
    }
}
