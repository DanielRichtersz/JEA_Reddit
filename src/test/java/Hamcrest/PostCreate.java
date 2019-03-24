package Hamcrest;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.PostTestResources.*;
import static Hamcrest.RedditorTestResources.*;
import static Hamcrest.SubredditTestResources.*;
import static io.restassured.RestAssured.given;

public class PostCreate {

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
    public void whenCreatingPost_ThenSuccesfull() {
        given().params("username", USERNAME, "title", POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void whenCreatingPost_RedditorNotFound() {
        given().params("username", INCORRECT_USERNAME, "title", POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + " /posts")
                .then()
                .statusCode(404);
    }

    @Test
    public void whenCreatingPost_SubredditNotFound() {
        given().params("username", USERNAME, "title", POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + INCORRECT_SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(404);
    }


    @Test
    public void whenCreatingPost_NoValidTitle() {
        given().params("username", USERNAME, "title", INCORRECT_EMPTY_POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(400);

        given().params("username", USERNAME, "title", INCORRECT_DELETED_POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(400);
    }
}
