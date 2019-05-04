package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubredditRepository extends CrudRepository<Subreddit, Long>
{
    Subreddit getByName(String name);
    List<Subreddit> findAllByFollowersIsContaining(Redditor redditor);
    List<Subreddit> findByNameContainingOrDescriptionContaining(String name, String description);
}
