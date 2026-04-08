package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class TestDataManager {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, Object> data;

    public TestDataManager(String resourcePath) {
        this.data = loadData(resourcePath);
    }

    public String getRequiredValue(String path) {
        Object current = data;
        for (String key : path.split("\\.")) {
            if (!(current instanceof Map)) {
                throw new IllegalStateException("Caminho de massa invalido: " + path);
            }

            current = ((Map<?, ?>) current).get(key);
            if (current == null) {
                throw new IllegalStateException("Valor obrigatorio nao encontrado: " + path);
            }
        }

        return String.valueOf(current);
    }

    private Map<String, Object> loadData(String resourcePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Arquivo de massa nao encontrado: " + resourcePath);
            }

            return OBJECT_MAPPER.readValue(inputStream, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException exception) {
            throw new IllegalStateException("Falha ao carregar massa de teste: " + resourcePath, exception);
        }
    }
}
