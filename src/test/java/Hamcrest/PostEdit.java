package Hamcrest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Hamcrest.PostTestResources.*;
import static Hamcrest.RedditorTestResources.*;
import static Hamcrest.SubredditTestResources.*;
import static io.restassured.RestAssured.given;

public class PostEdit {

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
    public void whenEditingPost_EditingSuccesfull() {
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

        given().params("content", NEW_POST_CONTENT, "username", USERNAME)
                .when()
                .request("PUT", "/subreddits/" + SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(202);
    }

    @Test
    public void whenEditingPost_SubredditNotFound() {
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

        given().params("content", NEW_POST_CONTENT, "username", USERNAME)
                .when()
                .request("PUT", "/subreddits/" + INCORRECT_SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(404);
    }

    @Test
    public void whenEditingPost_PostNotFound() {
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

        int id = 999999;

        given().params("content", NEW_POST_CONTENT, "username", USERNAME)
                .when()
                .request("PUT", "/subreddits/" + SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(404);
    }

    @Test
    public void whenEditingPost_PostWasDeleted() {
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

        given().params("subredditname", SUBREDDIT_NAME, "username", USERNAME)
                .when()
                .request("DELETE", "/redditors/" + USERNAME + " /posts/" + id)
                .then()
                .statusCode(202);

        given().params("content", NEW_POST_CONTENT, "username", USERNAME)
                .when()
                .request("PUT", "/subreddits/" + SUBREDDIT_NAME + "/posts/" + id + "/" + POST_TITLE)
                .then()
                .statusCode(403);
    }
}
