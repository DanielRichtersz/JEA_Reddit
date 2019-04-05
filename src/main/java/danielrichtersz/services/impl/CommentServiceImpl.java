package danielrichtersz.services.impl;

import danielrichtersz.models.Comment;
import danielrichtersz.services.interfaces.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    @Override
    public Comment createComment(String content, String subredditName, String username) {
        return null;
    }

    @Override
    public Comment updateComment(Long commentId, String newContent) {
        return null;
    }

    @Override
    public List<Comment> findComment(String searchTerm) {
        return null;
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return null;
    }

    @Override
    public void deleteComment(Long commentId) {

    }
}
