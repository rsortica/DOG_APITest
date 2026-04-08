package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.equalTo;

public final class ResponseSpecs {

    private ResponseSpecs() {
    }

    public static ResponseSpecification status200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("status", equalTo("success"))
                .build();
    }
}
