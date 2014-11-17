package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RowTest {

    Row row;
    Column column;

    @Before
    public void setup() {
        row = new Row();
        column = new Column(DataType.BIGINT, 0, 11, "id");
        row.put(column, 8L);
    }

    @Test
    public void testGetValue() throws Exception {
        Object o = row.get(column);
        assertThat(o.toString().equals(String.valueOf(8L)));
    }
}
