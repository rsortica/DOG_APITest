package tests;

import models.BreedListResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

@Tag("smoke")
@Tag("regression")
class BreedTests extends ApiTestSupport {

    @Test
    @DisplayName("Deve listar todas as raças com sucesso")
    void deveListarTodasAsRacasComSucesso() {
        String expectedBreed = testDataManager.getRequiredValue("listAllBreeds.expectedBreed");

        BreedListResponse response = dogApiClient.listAllBreeds()
                .then()
                .spec(ResponseSpecs.status200())
                .body(matchesJsonSchemaInClasspath("schemas/breed-list-schema.json"))
                .extract()
                .as(BreedListResponse.class);

        assertThat(response.getMessage().size(), greaterThan(0));
        assertThat(response.getMessage().keySet(), hasItem(expectedBreed));
    }
}
