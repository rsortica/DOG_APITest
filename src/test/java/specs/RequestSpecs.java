package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import utils.ConfigManager;

public final class RequestSpecs {

    private RequestSpecs() {
    }

    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBasePath("/api")
                .setConfig(restAssuredConfig())
                .addFilter(new AllureRestAssured())
                .build();
    }

    private static RestAssuredConfig restAssuredConfig() {
        return RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", ConfigManager.getConnectionTimeoutMillis())
                        .setParam("http.socket.timeout", ConfigManager.getSocketTimeoutMillis())
                        .setParam("http.connection-manager.timeout", (long) ConfigManager.getConnectionTimeoutMillis()));
    }
}
