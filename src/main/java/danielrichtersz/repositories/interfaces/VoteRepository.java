package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.*;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByOwnerAndPostable(Redditor owner, Postable postable);
}
