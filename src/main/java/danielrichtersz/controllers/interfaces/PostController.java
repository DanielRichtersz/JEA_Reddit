package danielrichtersz.controllers.interfaces;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface PostController {
    ResponseEntity createPost(
            String subredditName,
            String username,
            String title,
            String content);

    ResponseEntity editPost(
            String subredditName,
            String title,
            Long postId,
            String content,
            String username);

    ResponseEntity searchForPost(String title);

    ResponseEntity getPost(String subredditName, Long postId, String postTitle);
}
