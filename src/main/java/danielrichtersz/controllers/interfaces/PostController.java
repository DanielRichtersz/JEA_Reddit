package danielrichtersz.controllers.interfaces;

import org.springframework.http.ResponseEntity;

public interface PostController {
    ResponseEntity CreatePost(String subredditName, String username, String title, String content);
}
