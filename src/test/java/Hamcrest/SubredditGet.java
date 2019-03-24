package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.PASSWORD;
import static Hamcrest.RedditorTestResources.USERNAME;
import static Hamcrest.SubredditTestResources.*;
import static Hamcrest.SubredditTestResources.SUBREDDIT_NAME;
import static io.restassured.RestAssured.given;

public class SubredditGet {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
        RestAssured.port = 8080;

        // Create Redditor
        given().params("name", USERNAME, "password", PASSWORD)
                .when()
                .request("POST", "/redditors");

        // Create Subreddit
        given().params("name", SUBREDDIT_NAME, "description", SUBREDDIT_DESCRIPTION, "username", USERNAME)
                .when()
                .request("POST", "/subreddits");
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
    public void whenRequestingSubreddit_ThenSuccesfull() {
        given().when()
                .request("GET", "/subreddits/" + SUBREDDIT_NAME)
                .then()
                .statusCode(302);
    }

    @Test
    public void whenRequestingSubreddit_SubredditNotFound() {
        given().when()
                .request("GET", "/subreddits/" + INCORRECT_SUBREDDIT_NAME)
                .then()
                .statusCode(404);
    }
}
