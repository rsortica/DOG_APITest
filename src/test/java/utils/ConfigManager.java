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
}
