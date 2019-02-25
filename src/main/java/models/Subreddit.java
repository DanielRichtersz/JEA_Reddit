package models;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subreddit {
    private String name;

    private List<Post> posts;
    private List<Redditor> moderators;
    private List<Redditor> followers;

    public Subreddit(String name) {
        this.name = name;
    }
}
