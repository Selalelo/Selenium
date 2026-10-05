package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestConfig {
    private static final Properties PROPERTIES = loadProperties();

    private TestConfig() {
    }

    public static String getBaseUrl() {
        return getValue("base.url");
    }

    public static int getWaitTimeoutSeconds() {
        String value = getValue("wait.timeout.seconds");
        try {
            int timeout = Integer.parseInt(value);
            if (timeout < 1) {
                throw new IllegalArgumentException("wait.timeout.seconds must be greater than zero");
            }
            return timeout;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("wait.timeout.seconds must be a positive integer", exception);
        }
    }

    private static String getValue(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration value: " + key);
        }
        return value.trim();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Test configuration file config.properties was not found");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read config.properties", exception);
        }
    }
}
