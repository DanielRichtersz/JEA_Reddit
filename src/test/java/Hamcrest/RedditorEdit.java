package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


public class RedditorEdit {

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
        given().params("username", USERNAME, "password", NEW_PASSWORD)
                .when()
                .request("DELETE", "/redditors");

        given().params("username", USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors");
    }

    @Test
    public void whenEditingRedditorPassword_ThenEdited() {
        given().params("username", USERNAME, "newpassword", NEW_PASSWORD, "oldpassword", PASSWORD)
                .when()
                .request("PUT", "/redditors")
                .then()
                .statusCode(202);
    }

    @Test
    public void whenEditingRedditorIncorrectPassword_ThenNotAuthorized() {
        given().params("username", USERNAME, "newpassword", NEW_PASSWORD, "oldpassword", INCORRECT_PASSWORD)
                .when()
                .request("PUT", "/redditors")
                .then()
                .statusCode(401);
    }
}
