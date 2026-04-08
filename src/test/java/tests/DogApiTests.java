package tests;

import base.BaseTest;
import clients.DogApiClient;
import models.BreedListResponse;
import models.RandomImageResponse;
import models.SubBreedListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;
import utils.TestDataManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

class DogApiTests extends BaseTest {

    private DogApiClient dogApiClient;
    private TestDataManager testDataManager;

    @BeforeEach
    void setUp() {
        dogApiClient = new DogApiClient(requestSpec());
        testDataManager = new TestDataManager("testdata/dog-api-data.json");
    }

    @Test
    @DisplayName("Deve listar todas as racas com sucesso")
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

    @Test
    @DisplayName("Deve listar subracas de hound com sucesso")
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

    @Test
    @DisplayName("Deve retornar imagem aleatoria da raca hound")
    void deveRetornarImagemAleatoriaDaRacaHound() {
        String breed = testDataManager.getRequiredValue("randomImage.breed");
        String expectedPathSegment = testDataManager.getRequiredValue("randomImage.expectedPathSegment");

        RandomImageResponse response = dogApiClient.getRandomImageByBreed(breed)
                .then()
                .spec(ResponseSpecs.status200())
                .body(matchesJsonSchemaInClasspath("schemas/random-image-schema.json"))
                .extract()
                .as(RandomImageResponse.class);

        assertThat(response.getMessage(), not(emptyString()));
        assertThat(response.getMessage(), containsString(expectedPathSegment));
        assertThat(response.getStatus(), equalTo("success"));
    }
}
