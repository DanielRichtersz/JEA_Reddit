package danielrichtersz.services.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;

import java.util.List;

public interface CommentService {
    Comment createComment(String content, String username, Long postableId);

    Comment updateComment(Long commentId, String newContent);

    List<Comment> findComment(String searchTerm);

    Comment findCommentById(Long commentId);

    List<Comment> findCommentsByPostableId(Long postId);

    void deleteComment(Long commentId);
}
