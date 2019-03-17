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
    /**
     * MultiReddit should always contain 1 multiReddit which is the TimeLine of the user
     * The TimeLine multireddit contains all the subreddits the user follows in one collection
     */

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultiReddit> multiReddits;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Vote> votes;


    public Redditor() {

    }

    public Redditor(String username, String password) {
        this.username = username;
        this.password = password;
        this.multiReddits = new ArrayList<>();
        this.multiReddits.add(new MultiReddit(username + "Timeline", this));
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public List<MultiReddit> getMultiReddits() {
        return this.multiReddits;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public boolean checkPassword(String givenPassword) {
        return (this.password == givenPassword);
    }

    public boolean checkId(Long givenId) {
        return (this.id == givenId);
    }

    public void addNewMultiReddit(String name) {
        this.multiReddits.add(new MultiReddit(name, this));
    }

    @Override
    public String toString() {
        return String.format(
                "Redditor[id=%d, username=%s, password=%s]",
                id, username, password
        );
    }
}
