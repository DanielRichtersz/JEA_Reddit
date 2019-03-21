package danielrichtersz.controllers.interfaces;

import org.springframework.http.ResponseEntity;

public interface PostController {
    ResponseEntity createPost(String subredditName, String username, String title, String content);
    ResponseEntity editPost(String subredditName, String username, String content, String title);
}
