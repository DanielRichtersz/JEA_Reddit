package danielrichtersz.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

public interface CommentController {
    ResponseEntity createComment(
            String subredditName,
            String username,
            String content);

    ResponseEntity editComment(
            String subredditName,
            Long commentId,
            String content,
            String username);

    ResponseEntity searchForComment(String title);

    ResponseEntity getComment(String subredditName, Long postId);

    ResponseEntity deleteComment(Long commentId, String username);

    ResponseEntity upvoteOrDownvoteComment(String username, Long commentId, String voteType);
}
