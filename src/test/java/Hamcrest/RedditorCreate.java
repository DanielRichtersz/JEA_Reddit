package Hamcrest;

import danielrichtersz.models.Redditor;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.PASSWORD;
import static Hamcrest.RedditorTestResources.USERNAME;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class RedditorCreate {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.port = 8080;
    }

    //Tests whether or not the a timeline multireddit is automatically created for a new user
    @Test
    public void givenRedditor_whenCreatedReturnMultiredditListWithSizeOne_thenCorrect() {
        Redditor redditor = new Redditor("username1", "password1");
        assertThat(redditor.getMultiReddits(), hasSize(1));
    }

    @Test
    public void whenRequestedRedditor_ThenCreated() {
        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors")
                .then()
                .statusCode(201);
    }

    @Test
    public void existingUsername_RedditorNotCreated() {
        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors")
                .then()
                .statusCode(201);

        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors")
                .then()
                .statusCode(409);
    }

    @After
    public void reset() {
        given().params("username", USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors");
    }
}
