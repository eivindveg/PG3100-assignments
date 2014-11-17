package no.student.westerdals.vegeiv13.innlevering2.models;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.fest.assertions.Assertions.assertThat;
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
        assertThat(DataType.BIGINT == column.getDataType());
        assertThat(column.getIndex() == 0);
        assertThat(column.getName().equals("id"));
    }

    @Test
    public void testNoArgConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = Column.class.getDeclaredConstructor((Class<?>[]) null);
        int modifiers = constructor.getModifiers();

        // Assert that the constructor is private, so we can catch this and rewrite this test if the constructor
        // needs a proper test.
        assertTrue(Modifier.isPrivate(modifiers));

        // Make accessible so we can instantiate a parser.
        constructor.setAccessible(true);

        // Instantiate, test fails if instantiation fails(Like if an actual constructor
        // that does take parameters is implemented)
        constructor.newInstance((Class<?>[]) null);
    }

    @Test
    public void testPrimaryKey() {
        assertThat(!column.isPrimaryKey());
        column.setPrimaryKey(true);
        assertThat(column.isPrimaryKey());
        column.setPrimaryKey(false);
        assertThat(!column.isPrimaryKey());
    }

    @Test
    public void testToString() {
        String expected = "Column{name='id', index=0, dataType=BIGINT, width=11}";
        String actual = column.toString();
        assertThat(expected.equals(actual));
    }
}
