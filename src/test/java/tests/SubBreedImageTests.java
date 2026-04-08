package tests;

import models.ImageListResponse;
import models.RandomImageResponse;
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

class SubBreedImageTests extends ApiTestSupport {

    @ParameterizedTest(name = "Deve retornar imagem aleatória da sub-raça {1} de {0}")
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

    @ParameterizedTest(name = "Deve listar imagens da sub-raça {1} de {0}")
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
}
