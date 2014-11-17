package no.student.westerdals.vegeiv13.innlevering2.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
public class ConfigurationHandlerTest {

    private ConfigurationHandler configurationHandler;

    @Before
    public void setup() throws ConfigurationException {
        configurationHandler = ConfigurationHandler.getInstance();
    }

    @Test
    public void testSingleton() throws ConfigurationException {
        ConfigurationHandler configurationHandler2 = ConfigurationHandler.getInstance();
        assertThat(configurationHandler.equals(configurationHandler2));
    }

    @Test
    public void testConfigLoaded() {
        Configuration configuration = configurationHandler.getConfiguration();
        assertThat(configuration.containsKey("database"));
    }

}
