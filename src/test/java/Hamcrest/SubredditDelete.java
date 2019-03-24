package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.RedditorTestResources.*;
import static Hamcrest.SubredditTestResources.*;
import static io.restassured.RestAssured.given;

public class SubredditDelete {

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
    public void whenDeletingSubreddit_ThenSuccesfull() {
        given().params("subredditname", SUBREDDIT_NAME, "username", USERNAME)
                .when()
                .request("DELETE", "/subreddits")
                .then()
                .statusCode(202);
    }

    @Test
    public void whenDeletingSubreddit_SubredditNotFound() {
        given().params("subredditname", INCORRECT_SUBREDDIT_NAME, "username", USERNAME)
                .when()
                .request("DELETE", "/subreddits")
                .then()
                .statusCode(404);
    }

    @Test
    public void whenDeletingSubreddit_RedditorNotFound() {
        given().params("subredditname", SUBREDDIT_NAME, "username", INCORRECT_USERNAME)
                .when()
                .request("DELETE", "/subreddits")
                .then()
                .statusCode(404);
    }

}
