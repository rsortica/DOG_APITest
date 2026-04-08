package tests;

import models.SubBreedListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

@Tag("smoke")
@Tag("regression")
class SubBreedTests extends ApiTestSupport {

    @Test
    @DisplayName("Deve listar sub-raças de hound com sucesso")
    void deveListarSubRacasDeHoundComSucesso() {
        String breed = testDataManager.getRequiredValue("subBreeds.breed");
        String expectedSubBreed = testDataManager.getRequiredValue("subBreeds.expectedSubBreed");

        SubBreedListResponse response = dogApiClient.listSubBreeds(breed)
                .then()
                .spec(ResponseSpecs.status200())
                .body(matchesJsonSchemaInClasspath("schemas/sub-breed-list-schema.json"))
                .extract()
                .as(SubBreedListResponse.class);

        assertThat(response.getMessage().size(), greaterThan(0));
        assertThat(response.getMessage(), hasItem(expectedSubBreed));
    }
}
