package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;

public interface SubredditService {
    Subreddit createSubreddit(String name, String description, String username);

    Subreddit findByName(String name);

    boolean deleteSubreddit(String subredditName, String username);

    Subreddit updateSubreddit(String subredditName, String description);
}
