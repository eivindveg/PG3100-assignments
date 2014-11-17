package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class DataTypeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupported() {
        DataType.getTypeForString("ASDFASDF");
    }

    @Test
    public void testSQLNames() {
        DataType varchar = DataType.getTypeForString("VARCHAR");
        assertThat(varchar == DataType.VARCHAR);
        DataType dateTime = DataType.getTypeForString("DATETIME");
        assertThat(varchar == DataType.DATETIME);
    }
}
