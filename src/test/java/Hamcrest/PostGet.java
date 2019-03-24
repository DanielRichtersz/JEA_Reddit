package Hamcrest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.PostTestResources.POST_CONTENT;
import static Hamcrest.PostTestResources.POST_TITLE;
import static Hamcrest.RedditorTestResources.*;
import static Hamcrest.RedditorTestResources.USERNAME;
import static Hamcrest.SubredditTestResources.*;
import static io.restassured.RestAssured.given;

public class PostGet {

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
    public void whenDeletingPost_ThenSuccesfull() {
        //Create post and get ID
        Response response = given().params("username", USERNAME, "title", POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();

        ResponseBody body = response.getBody();

        JsonPath jsonPathEvaluator = response.jsonPath();

        int id = jsonPathEvaluator.get("id");

        given().when()
                .request("GET", "/subreddits/" + SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(302);
    }

    @Test
    public void whenDeletingPost_SubredditNotFound() {
        //Create post and get ID
        Response response = given().params("username", USERNAME, "title", POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();

        ResponseBody body = response.getBody();

        JsonPath jsonPathEvaluator = response.jsonPath();

        int id = jsonPathEvaluator.get("id");

        given().when()
                .request("GET", "/subreddits/" + INCORRECT_SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(404);
    }

    @Test
    public void whenDeletingPost_PostNotFound() {
        //Create post
        given().params("username", USERNAME, "title", POST_TITLE, "content", POST_CONTENT)
                .when()
                .request("POST", "/subreddits/" + SUBREDDIT_NAME + "/posts")
                .then()
                .statusCode(201);

        int id = 999999;

        given().when()
                .request("GET", "/subreddits/" + SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(404);
    }
}
