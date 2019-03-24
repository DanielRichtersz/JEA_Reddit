package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByOwnerIdAndPostId(Long ownerId, Long postId);

    Vote findByOwnerIdAndCommentId(Long ownerId, Long postId);

    Vote findByOwnerAndPost(Redditor owner, Post post);

    Vote findByOwnerAndComment(Redditor owner, Comment comment);
}
