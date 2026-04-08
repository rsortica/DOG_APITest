package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final String DEFAULT_ENV = "dev";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigManager() {
    }

    public static String getBaseUrl() {
        return getRequiredProperty("base.url");
    }

    public static int getConnectionTimeoutMillis() {
        return getIntProperty("timeout.connection.ms", 5000);
    }

    public static int getSocketTimeoutMillis() {
        return getIntProperty("timeout.socket.ms", 10000);
    }

    public static long getResponseTimeThresholdMillis() {
        return getLongProperty("timeout.response.ms", 15000L);
    }

    private static Properties loadProperties() {
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = DEFAULT_ENV;
        }
        String path = String.format("config/%s.properties", env);

        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalStateException("Arquivo de configuracao nao encontrado: " + path);
            }

            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Falha ao carregar configuracao do ambiente: " + env, exception);
        }
    }

    private static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Propriedade obrigatoria nao configurada: " + key);
        }
        return value;
    }

    private static int getIntProperty(String key, int defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Propriedade inteira invalida: " + key, exception);
        }
    }

    private static long getLongProperty(String key, long defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalStateException("Propriedade numerica invalida: " + key, exception);
        }
    }
}
