package no.westerdals.student.vegeiv13.innlevering3.utils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnectionSourceManagerTest {

    @Test
    public void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = ConnectionSourceManager.class.getDeclaredConstructor();
        int modifiers = constructor.getModifiers();

        // Assert that the constructor is private, so we can catch this and rewrite this test if the constructor
        // needs a proper test.
        assertTrue(Modifier.isPrivate(modifiers));

        // Make accessible so we can instantiate a parser.
        constructor.setAccessible(true);

        // Instantiate, test fails if instantiation fails(Like if an actual constructor
        // that does take parameters is implemented)
        constructor.newInstance();
    }

    @Test
    public void testMapConfigurationToJdbcConnectionSource() throws SQLException {
        Configuration configuration = new XMLConfiguration();
        configuration.setProperty("database.schema", "TestSchema");
        configuration.setProperty("database.url", "definitelyNotLocalHost");
        configuration.setProperty("database.type", "mysql");
        configuration.setProperty("user", "notRoot");
        configuration.setProperty("password", null);

        JdbcConnectionSource jdbcConnectionSource = ConnectionSourceManager.mapConfigurationToJdbcConnectionSource(configuration);
        assertNotNull(jdbcConnectionSource);
        try {
            jdbcConnectionSource.getReadWriteConnection();
            assertTrue(false);
        } catch (SQLException e) {
            assertTrue(true);
        }

    }

}
