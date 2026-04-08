package tests;

import models.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Tag("regression")
class NegativeContractTests extends ApiTestSupport {

    @Test
    @DisplayName("Deve retornar erro contratual ao consultar sub-raças de uma raça inválida")
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
    @DisplayName("Deve retornar erro contratual ao buscar imagem aleatória de uma raça inválida")
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
    @DisplayName("Deve retornar erro contratual ao buscar imagem de sub-raça inválida")
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
    @DisplayName("Deve retornar erro contratual ao listar imagens de uma raça inválida")
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
    @DisplayName("Deve retornar erro contratual ao listar imagens de sub-raça inválida")
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
}
