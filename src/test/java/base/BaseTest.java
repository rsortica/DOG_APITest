package base;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import specs.RequestSpecs;
import utils.ConfigManager;

public abstract class BaseTest {

    @BeforeAll
    static void globalSetup() {
        RestAssured.baseURI = ConfigManager.getBaseUrl();
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    protected RequestSpecification requestSpec() {
        return RestAssured.given()
                .spec(RequestSpecs.defaultSpec())
                .contentType(ContentType.JSON);
    }
}
