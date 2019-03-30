package danielrichtersz.services.impl;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Vote;
import danielrichtersz.models.enums.TypeVote;
import danielrichtersz.repositories.interfaces.CommentRepository;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
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

    @Autowired
    private RedditorRepository redditorRepository;

    @Override
    public Vote updateOrCreateVote(Long postId, String username, TypeVote typeVote) {
        Post post = postRepository.findById(postId).get();
        Redditor redditor = redditorRepository.findByUsername(username);
        Vote vote = voteRepository.findByOwnerAndPost(redditor, post);
        if (vote == null) {
            return voteRepository.save(new Vote(post, redditor, typeVote));
        }
        vote.setTypeVote(typeVote);
        return voteRepository.save(vote);
    }

    @Override
    public void deleteVote(Long postId, String username) {
        Post post = postRepository.findById(postId).get();
        Redditor redditor = redditorRepository.findByUsername(username);
        Vote vote = voteRepository.findByOwnerAndPost(redditor, post);

        if (vote != null) {
            voteRepository.delete(vote);
        }
    }

    @Override
    public Vote getVote(Long postId, String username) {
        Post post = postRepository.findById(postId).get();
        Redditor redditor = redditorRepository.findByUsername(username);
        return voteRepository.findByOwnerAndPost(redditor, post);
    }
}
