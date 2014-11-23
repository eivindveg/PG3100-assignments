package no.student.westerdals.vegeiv13.innlevering2.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ConfigurationHandler {

    private static final Object threadLock = new Object();
    private static ConfigurationHandler instance;
    private final Configuration configuration;

    private ConfigurationHandler() {
        Configuration tempConf;
        try {
            tempConf = new XMLConfiguration("config.xml");
        } catch (ConfigurationException e) {
            try {
                tempConf = new XMLConfiguration("conf/config.xml");
            } catch (ConfigurationException e1) {
                System.err.println("Unable to load configuration, using default values");
                tempConf = new XMLConfiguration();
            }
        }
        configuration = tempConf;
    }

    public static ConfigurationHandler getInstance() {
        if (instance == null) {
            synchronized (threadLock) {
                if (instance == null) {
                    instance = new ConfigurationHandler();
                }
            }
        }
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
