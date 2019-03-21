package danielrichtersz.services.impl;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Vote;
import danielrichtersz.models.enums.TypeVote;
import danielrichtersz.repositories.interfaces.CommentRepository;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.VoteRepository;
import danielrichtersz.services.interfaces.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote createVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Vote updateVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Post upvotePost(Post post, Redditor redditor, TypeVote vote) {
        post.addVote(new Vote(post, redditor, vote));
        return postRepository.save(post);
    }

    @Override
    public Comment upvoteComment(Comment comment, Redditor redditor, TypeVote vote) {
        comment.addVote(new Vote(comment, redditor, vote));
        return commentRepository.save(comment);
    }

    @Override
    public Vote getVote(Post post, Redditor owner) {
        return voteRepository.findByOwnerAndPost(owner, post);
    }

    @Override
    public Vote getVote(Comment comment, Redditor owner) {
        return voteRepository.findByOwnerAndComment(owner, comment);
    }

    @Override
    public void deleteVote(Vote vote) {
        voteRepository.delete(vote);
    }
}
