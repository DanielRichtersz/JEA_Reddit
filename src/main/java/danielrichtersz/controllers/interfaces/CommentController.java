package danielrichtersz.controllers.interfaces;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CommentController {
    ResponseEntity createComment(
            String username,
            String content,
            Long postableId);

    ResponseEntity editComment(
            String subredditName,
            Long commentId,
            String content,
            String username);

    ResponseEntity searchForComment(String title);

    ResponseEntity getComment(
            Long commentId);

    ResponseEntity getCommentsFromPost(Long postId);

    @GetMapping("comments/{commentId}/comments")
    ResponseEntity getCommentsFromComment(
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "commentId") Long commentId);

    ResponseEntity deleteComment(Long commentId, String username);

    ResponseEntity upvoteOrDownvoteComment(String username, Long commentId, String voteType);
}
