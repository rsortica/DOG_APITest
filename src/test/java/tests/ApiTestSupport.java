package tests;

import base.BaseTest;
import clients.DogApiClient;
import org.junit.jupiter.api.BeforeEach;
import utils.TestDataManager;

import java.util.Map;
import java.util.stream.Stream;

abstract class ApiTestSupport extends BaseTest {

    private static final TestDataManager STATIC_TEST_DATA = new TestDataManager("testdata/dog-api-data.json");

    protected DogApiClient dogApiClient;
    protected TestDataManager testDataManager;

    @BeforeEach
    void setUpApiTestSupport() {
        dogApiClient = new DogApiClient(requestSpec());
        testDataManager = new TestDataManager("testdata/dog-api-data.json");
    }

    protected static Stream<Map<String, Object>> positiveBreeds() {
        return STATIC_TEST_DATA.getRequiredList("positiveBreeds").stream();
    }

    protected static Stream<Map<String, Object>> positiveSubBreeds() {
        return STATIC_TEST_DATA.getRequiredList("positiveSubBreeds").stream();
    }
}
