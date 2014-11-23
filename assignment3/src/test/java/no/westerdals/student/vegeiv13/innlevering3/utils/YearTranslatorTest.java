package no.westerdals.student.vegeiv13.innlevering3.utils;

import com.j256.ormlite.field.FieldConverter;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;

import static org.junit.Assert.*;

public class YearTranslatorTest {

    private YearTranslator yearTranslator;

    @Before
    public void setup() {
        yearTranslator = YearTranslator.getSingleton();
    }

    @Test
    public void testTranslatorIsORMLiteMapper() {
        assertTrue("Translator can be used to map field types", yearTranslator instanceof FieldConverter);
    }

    @Test
    public void testJavaToSqlArg() {
        int expected = 2001;
        Year baseYear = Year.of(expected);
        int actual = (int) (yearTranslator.javaToSqlArg(null, baseYear));
        assertEquals("Translator turns year objects into ints", actual, expected);

        assertNull("Translator returns null when it gets no data", yearTranslator.javaToSqlArg(null, null));
    }

    @Test
    public void testSqlArgToJava() {
        Year expected = Year.of(2001);
        int baseYear = 2001;
        Year actual = (Year) (yearTranslator.sqlArgToJava(null, baseYear, 0));
        assertEquals("Translator turns sql args to java objects", actual, expected);
    }
}
