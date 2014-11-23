package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class TableTest {

    private Table table;

    @Before
    public void setUp() throws Exception {
        table = new Table("TestTable", new ArrayList<>());
        table.setName("TestTable");
        assertEquals(table.getName(), "TestTable");
        Column column = new Column(DataType.VARCHAR, 1, 11, "test");
        table.addColumn(column);
        assertEquals(table.getColumns().size(), 2);
        Row row = new Row();
        row.put(column, "TestValue");
        table.addRow(row);
        assertEquals(table.getKnownRows().size(), 1);
    }

    @Test
    public void testReplaceKnownRows() throws Exception {
        table.setKnownRows(new ArrayList<>());
        assertEquals(table.getKnownRows().size(), 0);
    }
    @Test
    public void testDescribeTable() throws Exception {
        String expected = "Describing table 'TestTable':\n" +
                "id                            test                          \n" +
                "TestValue                     \n";
        String actual = table.describe();
        assertEquals(actual, expected);
    }

    @Test
    public void testToString() {
        String expected = "Table{name='TestTable', knownRows=[{Column{name='test', index=1, dataType=VARCHAR, width=11}=TestValue}], columns=[Column{name='id', index=0, dataType=BIGINT, width=11}, Column{name='test', index=1, dataType=VARCHAR, width=11}]}";
        String actual = table.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testCreateTableStatement() {
        String expected = "CREATE TABLE IF NOT EXISTS `TestTable` (`id` BIGINT(11) NOT NULL AUTO_INCREMENT,\n" +
                "`test` VARCHAR(11) NOT NULL,\n" +
                "PRIMARY KEY (`id`)\n" +
                ")ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";
        String actual = table.toCreateTableStatement();

        assertEquals(expected, actual);
    }
}
