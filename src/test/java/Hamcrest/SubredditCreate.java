package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.*;
import static Hamcrest.SubredditTestResources.SUBREDDIT_DESCRIPTION;
import static Hamcrest.SubredditTestResources.SUBREDDIT_NAME;
import static io.restassured.RestAssured.given;

public class SubredditCreate {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.port = 8080;

        // Create Redditor
        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors");
    }

    @After
    public void reset() {
        given().params("subredditname", SUBREDDIT_NAME, "username", USERNAME)
                .when()
                .request("DELETE", "/subreddits");

        given().params("username", USERNAME, "password", PASSWORD)
                .when()
                .request("DELETE", "/redditors");
    }

    @Test
    public void whenCreatingSubreddit_ThenSuccesfull() {
        given().params("name", SUBREDDIT_NAME, "description", SUBREDDIT_DESCRIPTION, "username", USERNAME)
                .when()
                .request("POST", "/subreddits")
                .then()
                .statusCode(201);
    }

    @Test
    public void whenCreatingSubreddit_SubredditAlreadyExists() {
        given().params("name", SUBREDDIT_NAME, "description", SUBREDDIT_DESCRIPTION, "username", USERNAME)
                .when()
                .request("POST", "/subreddits")
                .then()
                .statusCode(201);

        given().params("name", SUBREDDIT_NAME, "description", SUBREDDIT_DESCRIPTION, "username", USERNAME)
                .when()
                .request("POST", "/subreddits")
                .then()
                .statusCode(409);
    }

    @Test
    public void whenCreatingSubreddit_RedditorNotFound() {
        given().params("name", SUBREDDIT_NAME, "description", SUBREDDIT_DESCRIPTION, "username", INCORRECT_USERNAME)
                .when()
                .request("POST", "/subreddits")
                .then()
                .statusCode(404);
    }
}
