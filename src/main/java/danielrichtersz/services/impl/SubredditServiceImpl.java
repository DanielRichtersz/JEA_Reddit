package danielrichtersz.services.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import danielrichtersz.services.interfaces.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubredditServiceImpl implements SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private RedditorRepository redditorRepository;

    @Override
    public Subreddit createSubreddit(String name, String description, String username) {
        Redditor redditor = redditorRepository.findByUsername(username);
        return subredditRepository.save(new Subreddit(name, description, redditor));
    }

    @Override
    public Subreddit findByName(String name) {
        return subredditRepository.findByName(name);
    }

    @Override
    public boolean deleteSubreddit(String subredditName, String username) {
        try {
            Subreddit subreddit = subredditRepository.findByName(subredditName);
            subredditRepository.delete(subreddit);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Subreddit updateSubreddit(String subredditName, String description) {
        Subreddit subreddit = subredditRepository.findByName(subredditName);
        subreddit.setDescription(description);
        return subredditRepository.save(subreddit);
    }
}
