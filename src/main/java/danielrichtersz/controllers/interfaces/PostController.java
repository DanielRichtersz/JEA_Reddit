package danielrichtersz.controllers.interfaces;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/redditors/posts/{postid}")
    ResponseEntity deletePost(Long postId, String username);

    ResponseEntity upvoteOrDownvotePost(String username, Long postId, String voteType);
}
