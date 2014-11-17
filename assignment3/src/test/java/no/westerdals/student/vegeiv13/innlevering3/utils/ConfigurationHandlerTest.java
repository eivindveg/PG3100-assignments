package no.westerdals.student.vegeiv13.innlevering3.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigurationHandlerTest {

    private ConfigurationHandler instance;

    @Before
    public void setup() {
        instance = ConfigurationHandler.getInstance();
    }

    @Test
    public void testHasConfiguration() {
        assertNotNull(instance.getConfiguration());

    }
}
