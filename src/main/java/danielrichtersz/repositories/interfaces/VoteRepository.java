package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
}
