package models;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Redditor {
    private int id;

    /**
     * MultiReddit always contains 1 multiReddit which is the TimeLine of the user
     * The TimeLine multireddit contains all the subreddits the user follows in one collection
     */
    private List<MultiReddit> multiReddits;
    private List<Subreddit> moderatedSubreddits;
    private List<Post> posts;
    private List<Comment> comments;
    private List<Vote> votes;

    private String username;
    private String password;

    public Redditor() {

    }

    public Redditor(String username, String password) {
        this.username = username;
        this.password = password;
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.multiReddits = new ArrayList<>();
        this.moderatedSubreddits = new ArrayList<>();
        this.votes = new ArrayList<>();
    }
}
