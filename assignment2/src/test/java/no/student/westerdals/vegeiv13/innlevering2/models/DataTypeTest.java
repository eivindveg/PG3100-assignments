package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DataTypeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupported() {
        DataType.getTypeForString("ASDFASDF");
    }

    @Test
    public void testSQLNames() {
        DataType varchar = DataType.getTypeForString("VARCHAR");
        assertEquals(varchar, DataType.VARCHAR);
        DataType dateTime = DataType.getTypeForString("DATETIME");
        assertEquals(dateTime, DataType.DATETIME);
    }
}
