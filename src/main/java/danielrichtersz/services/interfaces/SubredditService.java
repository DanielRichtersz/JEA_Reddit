package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;

public interface SubredditService {
    Subreddit createSubreddit(String name, String description, Redditor owner);
    Subreddit findByName(String name);
}
