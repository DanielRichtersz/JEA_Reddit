package danielrichtersz.services.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import danielrichtersz.services.interfaces.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditServiceImpl implements SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private RedditorRepository redditorRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Subreddit createSubreddit(String name, String description, String username) {
        Redditor redditor = redditorRepository.findByUsername(username);
        return subredditRepository.save(new Subreddit(name, description, redditor));
    }

    @Override
    public Subreddit getByName(String name) {
        return subredditRepository.getByName(name);
    }

    @Override
    public boolean deleteSubreddit(String subredditName, String username) {
        try {
            Subreddit subreddit = subredditRepository.getByName(subredditName);
            subredditRepository.delete(subreddit);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Subreddit updateSubreddit(String subredditName, String description) {
        Subreddit subreddit = subredditRepository.getByName(subredditName);
        subreddit.setDescription(description);
        return subredditRepository.save(subreddit);
    }

    @Override
    public boolean addFollower(String username, String subredditName) {
        try {
            Redditor redditor = redditorRepository.findByUsername(username);
            Subreddit subreddit = subredditRepository.getByName(subredditName);

            if (!subreddit.getFollowers().contains(redditor)) {
                subreddit.addFollower(redditor);
                subredditRepository.save(subreddit);
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Subreddit> getFollowedSubreddits(String username) {
        try {
            Redditor redditor = redditorRepository.findByUsername(username);
            return subredditRepository.findAllByFollowersIsContaining(redditor);
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Subreddit> findSubredditsByNameOrDescriptionContaining(String searchTerm) {
        try {
            return subredditRepository.findByNameContainingOrDescriptionContaining(searchTerm, searchTerm);
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Redditor> getFollowers(String subredditName) {
        try {
            return subredditRepository.findByName(subredditName);
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public boolean getFollowingSubreddit(String username, String subredditName) {
        Redditor redditor = redditorRepository.findByUsername(username);
        Subreddit subreddit = subredditRepository.getByName(subredditName);

        if (subreddit.getFollowers().contains(redditor)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFollower(String username, String subredditName) {
        try {
            Redditor redditor = redditorRepository.findByUsername(username);
            Subreddit subreddit = subredditRepository.getByName(subredditName);

            if (subreddit.getFollowers().contains(redditor)) {
                subreddit.getFollowers().remove(redditor);
                subredditRepository.save(subreddit);
            }

            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
}
