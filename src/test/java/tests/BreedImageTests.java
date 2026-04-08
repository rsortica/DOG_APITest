package tests;

import models.ImageListResponse;
import models.RandomImageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import specs.ResponseSpecs;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

class BreedImageTests extends ApiTestSupport {

    @Test
    @DisplayName("Deve retornar imagem aleatória da raça hound")
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

    @ParameterizedTest(name = "Deve listar imagens da raça {0}")
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
}
