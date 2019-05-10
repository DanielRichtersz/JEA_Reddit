package danielrichtersz.services.interfaces;

import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;

import java.util.List;

public interface SubredditService {
    Subreddit createSubreddit(String name, String description, String username);

    Subreddit getByName(String name);

    boolean deleteSubreddit(String subredditName, String username);

    Subreddit updateSubreddit(String subredditName, String description);

    boolean addFollower(String username, String subredditName);

    List<Subreddit> getFollowedSubreddits(String username);

    List<Subreddit> findSubredditsByNameOrDescriptionContaining(String searchTerm);

    boolean getFollowingSubreddit(String username, String subredditName);

    boolean removeFollower(String username, String subredditName);
}
