package danielrichtersz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Redditor {

    @Id
    @GeneratedValue
    private Long id;

    //Unique constraint?
    private String username;

    private String password;

    private String role;

    private String token;

    /**
     * MultiReddit should always contain 1 multiReddit which is the TimeLine of the user
     * The TimeLine multireddit contains all the subreddits the user follows in one collection
     */

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultiReddit> multiReddits;

    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;

    @OneToMany(mappedBy = "owner")
    private List<Vote> votes;


    public Redditor() {

    }

    public Redditor(String username, String password) {
        this.username = username;
        this.password = password;
        this.multiReddits = new ArrayList<>();
        this.multiReddits.add(new MultiReddit(username + "Timeline", this));
        this.comments = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }
    public List<MultiReddit> getMultiReddits() {
        return this.multiReddits;
    }
    public List<Comment> getComments() {
        return this.comments;
    }
    public List<Vote> getVotes() {
        return this.votes;
    }
    public boolean passwordIsValid(String givenPassword) {
        return this.password.equals(givenPassword);
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void addNewMultiReddit(String name) {
        this.multiReddits.add(new MultiReddit(name, this));
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }

    public void addSubredditToMultireddit(Subreddit subreddit, String multiredditName) {
        for (MultiReddit multiReddit : this.multiReddits) {
            if (multiReddit.getName() == multiredditName) {
                if (!multiReddit.getSubreddits().contains(subreddit)) {
                    multiReddit.addSubReddit(subreddit);
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Redditor[id=%d, username=%s, password=%s]",
                id, username, password
        );
    }

    public final long getRedditorId() {
        return this.id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
