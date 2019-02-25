package models;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MultiReddit {
    private String name;
    private List<Subreddit> subreddits = new ArrayList<>();

    public MultiReddit(String name) {
        this.name = name;
    }

    public void editName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void addSubReddit(Subreddit subreddit) {
        this.subreddits.add(subreddit);
    }

    public void removeSubreddit(Subreddit subreddit) {
        this.subreddits.remove(subreddit);
    }
}
