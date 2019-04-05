package danielrichtersz.services.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;

import java.util.List;

public interface CommentService {
    Comment createComment(String content, String subredditName, String username);

    Comment updateComment(Long commentId, String newContent);

    List<Comment> findComment(String searchTerm);

    Comment findCommentById(Long commentId);

    void deleteComment(Long commentId);
}
