package tests;

import base.BaseTest;
import clients.DogApiClient;
import models.BreedListResponse;
import models.ErrorResponse;
import models.ImageListResponse;
import models.RandomImageResponse;
import models.SubBreedListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import specs.ResponseSpecs;
import utils.TestDataManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

class DogApiTests extends BaseTest {

    private static final TestDataManager STATIC_TEST_DATA = new TestDataManager("testdata/dog-api-data.json");
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

    @ParameterizedTest(name = "Deve listar imagens da raca {0}")
    @MethodSource("positiveBreeds")
    void deveListarImagensDaRacaConfigurada(Map<String, Object> data) {
        String breed = String.valueOf(data.get("breed"));
        String expectedPathSegment = String.valueOf(data.get("expectedPathSegment"));

        ImageListResponse response = dogApiClient.listImagesByBreed(breed)
                .then()
                .spec(ResponseSpecs.status200())
                .body(matchesJsonSchemaInClasspath("schemas/image-list-schema.json"))
                .extract()
                .as(ImageListResponse.class);

        assertThat(response.getStatus(), equalTo("success"));
        assertThat(response.getMessage().size(), greaterThan(0));
        assertThat(response.getMessage().get(0), containsString(expectedPathSegment));
    }

    @ParameterizedTest(name = "Deve retornar imagem aleatoria da subraca {1} de {0}")
    @MethodSource("positiveSubBreeds")
    void deveRetornarImagemAleatoriaDaSubRacaConfigurada(Map<String, Object> data) {
        String breed = String.valueOf(data.get("breed"));
        String subBreed = String.valueOf(data.get("subBreed"));
        String expectedPathSegment = String.valueOf(data.get("expectedPathSegment"));

        RandomImageResponse response = dogApiClient.getRandomImageBySubBreed(breed, subBreed)
                .then()
                .spec(ResponseSpecs.status200())
                .body(matchesJsonSchemaInClasspath("schemas/random-image-schema.json"))
                .extract()
                .as(RandomImageResponse.class);

        assertThat(response.getStatus(), equalTo("success"));
        assertThat(response.getMessage(), not(emptyString()));
        assertThat(response.getMessage(), containsString(expectedPathSegment));
    }

    @ParameterizedTest(name = "Deve listar imagens da subraca {1} de {0}")
    @MethodSource("positiveSubBreeds")
    void deveListarImagensDaSubRacaConfigurada(Map<String, Object> data) {
        String breed = String.valueOf(data.get("breed"));
        String subBreed = String.valueOf(data.get("subBreed"));
        String expectedPathSegment = String.valueOf(data.get("expectedPathSegment"));

        ImageListResponse response = dogApiClient.listImagesBySubBreed(breed, subBreed)
                .then()
                .spec(ResponseSpecs.status200())
                .body(matchesJsonSchemaInClasspath("schemas/image-list-schema.json"))
                .extract()
                .as(ImageListResponse.class);

        assertThat(response.getStatus(), equalTo("success"));
        assertThat(response.getMessage().size(), greaterThan(0));
        assertThat(response.getMessage().get(0), containsString(expectedPathSegment));
    }

    @Test
    @DisplayName("Deve retornar erro contratual ao consultar subracas de uma raca invalida")
    void deveRetornarErroAoConsultarSubRacasDeRacaInvalida() {
        String breed = testDataManager.getRequiredValue("invalidBreedSubBreeds.breed");
        String expectedMessage = testDataManager.getRequiredValue("invalidBreedSubBreeds.expectedMessage");

        ErrorResponse response = dogApiClient.listSubBreeds(breed)
                .then()
                .spec(ResponseSpecs.status404Error())
                .body(matchesJsonSchemaInClasspath("schemas/error-response-schema.json"))
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getMessage(), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Deve retornar erro contratual ao buscar imagem aleatoria de uma raca invalida")
    void deveRetornarErroAoBuscarImagemAleatoriaDeRacaInvalida() {
        String breed = testDataManager.getRequiredValue("invalidBreedRandomImage.breed");
        String expectedMessage = testDataManager.getRequiredValue("invalidBreedRandomImage.expectedMessage");

        ErrorResponse response = dogApiClient.getRandomImageByBreed(breed)
                .then()
                .spec(ResponseSpecs.status404Error())
                .body(matchesJsonSchemaInClasspath("schemas/error-response-schema.json"))
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getMessage(), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Deve retornar erro contratual ao buscar imagem de subraca invalida")
    void deveRetornarErroAoBuscarImagemDeSubRacaInvalida() {
        String breed = testDataManager.getRequiredValue("invalidSubBreedRandomImage.breed");
        String subBreed = testDataManager.getRequiredValue("invalidSubBreedRandomImage.subBreed");
        String expectedMessage = testDataManager.getRequiredValue("invalidSubBreedRandomImage.expectedMessage");

        ErrorResponse response = dogApiClient.getRandomImageBySubBreed(breed, subBreed)
                .then()
                .spec(ResponseSpecs.status404Error())
                .body(matchesJsonSchemaInClasspath("schemas/error-response-schema.json"))
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getMessage(), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Deve retornar erro contratual ao listar imagens de uma raca invalida")
    void deveRetornarErroAoListarImagensDeRacaInvalida() {
        String breed = testDataManager.getRequiredValue("invalidBreedImageList.breed");
        String expectedMessage = testDataManager.getRequiredValue("invalidBreedImageList.expectedMessage");

        ErrorResponse response = dogApiClient.listImagesByBreed(breed)
                .then()
                .spec(ResponseSpecs.status404Error())
                .body(matchesJsonSchemaInClasspath("schemas/error-response-schema.json"))
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getMessage(), equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Deve retornar erro contratual ao listar imagens de subraca invalida")
    void deveRetornarErroAoListarImagensDeSubRacaInvalida() {
        String breed = testDataManager.getRequiredValue("invalidSubBreedImageList.breed");
        String subBreed = testDataManager.getRequiredValue("invalidSubBreedImageList.subBreed");
        String expectedMessage = testDataManager.getRequiredValue("invalidSubBreedImageList.expectedMessage");

        ErrorResponse response = dogApiClient.listImagesBySubBreed(breed, subBreed)
                .then()
                .spec(ResponseSpecs.status404Error())
                .body(matchesJsonSchemaInClasspath("schemas/error-response-schema.json"))
                .extract()
                .as(ErrorResponse.class);

        assertThat(response.getMessage(), equalTo(expectedMessage));
    }

    static Stream<Map<String, Object>> positiveBreeds() {
        return STATIC_TEST_DATA.getRequiredList("positiveBreeds").stream();
    }

    static Stream<Map<String, Object>> positiveSubBreeds() {
        return STATIC_TEST_DATA.getRequiredList("positiveSubBreeds").stream();
    }
}
