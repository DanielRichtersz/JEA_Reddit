package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.*;
import static io.restassured.RestAssured.given;
import static io.swagger.annotations.OAuth2Definition.Flow.PASSWORD;

public class RedditorGet {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.port = 8080;

        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors");
    }

    @After
    public void reset() {
        given().params("username", USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors");
    }

    @Test
    public void whenRequestingRedditor_ThenFound() {
        given().when()
                .request("GET", "/redditors/" + USERNAME)
                .then()
                .statusCode(302);
    }

    @Test
    public void whenRequestingNonExistantRedditor_ThenNotFound() {
        given().when()
                .request("GET", "/redditors/" + INCORRECT_USERNAME)
                .then()
                .statusCode(404);
    }
}
