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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;

class DogApiTests extends BaseTest {

    private DogApiClient dogApiClient;

    @BeforeEach
    void setUp() {
        dogApiClient = new DogApiClient(requestSpec());
    }

    @Test
    @DisplayName("Deve listar todas as racas com sucesso")
    void deveListarTodasAsRacasComSucesso() {
        BreedListResponse response = dogApiClient.listAllBreeds()
                .then()
                .spec(ResponseSpecs.status200())
                .extract()
                .as(BreedListResponse.class);

        assertThat(response.getMessage().size(), greaterThan(0));
        assertThat(response.getMessage().keySet(), hasItem("hound"));
    }

    @Test
    @DisplayName("Deve listar subracas de hound com sucesso")
    void deveListarSubRacasDeHoundComSucesso() {
        SubBreedListResponse response = dogApiClient.listSubBreeds("hound")
                .then()
                .spec(ResponseSpecs.status200())
                .extract()
                .as(SubBreedListResponse.class);

        assertThat(response.getMessage().size(), greaterThan(0));
        assertThat(response.getMessage(), hasItem("afghan"));
    }

    @Test
    @DisplayName("Deve retornar imagem aleatoria da raca hound")
    void deveRetornarImagemAleatoriaDaRacaHound() {
        RandomImageResponse response = dogApiClient.getRandomImageByBreed("hound")
                .then()
                .spec(ResponseSpecs.status200())
                .extract()
                .as(RandomImageResponse.class);

        assertThat(response.getMessage(), not(emptyString()));
        assertThat(response.getMessage(), containsString("/breeds/hound"));
    }
}
