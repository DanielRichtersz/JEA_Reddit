package danielrichtersz.models;

import java.util.List;

public class Subreddit {
    private String name;

    private List<Post> posts;
    private List<Redditor> moderators;
    private List<Redditor> followers;

    public Subreddit(String name) {
        this.name = name;
    }
}
