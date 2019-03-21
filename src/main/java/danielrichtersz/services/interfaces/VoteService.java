package danielrichtersz.services.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Vote;
import danielrichtersz.models.enums.TypeVote;

public interface VoteService {

    Vote updateVote(Vote vote);

    Post upvotePost(Post post, Redditor redditor, TypeVote vote);

     Comment upvoteComment(Comment comment, Redditor redditor, TypeVote vote);

    Vote getVote(Post post, Redditor redditor);

    Vote getVote(Comment comment, Redditor redditor);

    void deleteVote(Vote vote);

    Vote createVote(Vote vote);
}
