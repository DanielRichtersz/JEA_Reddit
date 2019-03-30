package danielrichtersz.services.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Vote;
import danielrichtersz.models.enums.TypeVote;

public interface VoteService {

    Vote updateOrCreateVote(Long postId, String username, TypeVote typeVote);

    void deleteVote(Long postId, String username);

    Vote getVote(Long postId, String username);
}
