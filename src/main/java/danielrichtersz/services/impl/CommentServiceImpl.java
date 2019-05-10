package danielrichtersz.services.impl;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.CommentRepository;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import danielrichtersz.services.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private RedditorRepository redditorRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(String content, String username, Long postableId) {
        Redditor redditor = redditorRepository.findByUsername(username);
        Post post = postRepository.getById(postableId);
        Comment comment = commentRepository.getById(postableId);

        if (redditor == null || (post == null && comment == null)) {
            return null;
        };

        Comment newComment = new Comment(content, redditor, post == null ? comment : post);
        return commentRepository.save(newComment);
    }

    @Override
    public Comment updateComment(Long commentId, String newContent) {
        return null;
    }

    @Override
    public List<Comment> findComment(String searchTerm) {
        return commentRepository.findByContentContains(searchTerm);
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.getById(commentId);
    }


    @Override
    public List<Comment> findCommentsByPostableId(Long postId) {
        return commentRepository.findByParentPostableIdOrderByDateCreatedDesc(postId);
    }

    @Override
    public void deleteComment(Long commentId) {

    }
}
