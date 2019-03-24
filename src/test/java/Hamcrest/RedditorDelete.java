package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.*;
import static io.restassured.RestAssured.given;

public class RedditorDelete {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.port = 8080;

        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors");
    }

    @Test
    public void whenDeletingRedditor_ThenDeleted() {
        given().params("username", USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors")
                .then()
                .statusCode(202);
    }

    @Test
    public void whenDeletingRedditorWithIncorrectPassword_ThenNotAuthorized() {
        given().params("username", USERNAME, "password", INCORRECT_PASSWORD)
                .when()
                .request("DELETE", "/redditors")
                .then()
                .statusCode(401);
    }

    @Test
    public void whenDeletingRedditorWithIncorrectUsername_ThenNotFound() {
        given().params("username", INCORRECT_USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors")
                .then()
                .statusCode(404);
    }

    @After
    public void reset() {
        given().params("username", USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors");
    }
}
