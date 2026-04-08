package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecs {

    private RequestSpecs() {
    }

    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBasePath("/api")
                .addFilter(new AllureRestAssured())
                .build();
    }
}
