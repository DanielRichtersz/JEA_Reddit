package danielrichtersz.services.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import danielrichtersz.services.interfaces.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubredditServiceImpl implements SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;

    @Override
    public Subreddit createSubreddit(String name, String description, Redditor owner) {
        return subredditRepository.save(new Subreddit(name, description, owner));
    }

    @Override
    public Subreddit findByName(String name) {
        return subredditRepository.findByName(name);
    }

    @Override
    public Subreddit updateSubreddit(Subreddit subreddit) {
        return subredditRepository.save(subreddit);
    }
}
