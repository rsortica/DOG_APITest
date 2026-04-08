package clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DogApiClient {

    private final RequestSpecification requestSpecification;

    public DogApiClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response listAllBreeds() {
        return requestSpecification
                .when()
                .get("/breeds/list/all");
    }

    public Response listSubBreeds(String breed) {
        return requestSpecification
                .pathParam("breed", breed)
                .when()
                .get("/breed/{breed}/list");
    }

    public Response getRandomImageByBreed(String breed) {
        return requestSpecification
                .pathParam("breed", breed)
                .when()
                .get("/breed/{breed}/images/random");
    }
}
