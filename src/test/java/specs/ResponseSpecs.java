package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public final class ResponseSpecs {

    private ResponseSpecs() {
    }

    public static ResponseSpecification status200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectHeader("Content-Type", containsString("application/json"))
                .expectBody("status", equalTo("success"))
                .build();
    }

    public static ResponseSpecification status404Error() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectHeader("Content-Type", containsString("application/json"))
                .expectBody("status", equalTo("error"))
                .expectBody("code", equalTo(404))
                .build();
    }
}
