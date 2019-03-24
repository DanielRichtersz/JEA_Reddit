package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Subreddit;
import org.springframework.data.repository.CrudRepository;

public interface SubredditRepository extends CrudRepository<Subreddit, Long>
{
    Subreddit findByName(String name);
}
