package danielrichtersz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Redditor {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String passWord;
    /**
     * MultiReddit should always contain 1 multiReddit which is the TimeLine of the user
     * The TimeLine multireddit contains all the subreddits the user follows in one collection
     */

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultiReddit> multiReddits;

    @OneToMany
    private List<Post> posts;

    /*private List<Comment> comments;

    private List<Vote> votes;*/


    public Redditor() {

    }

    public Redditor(String username, String password) {
        this.username = username;
        this.passWord = password;
        this.multiReddits = new ArrayList<>();
        this.multiReddits.add(new MultiReddit(username + "Multireddit", this));
        /*this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.multiReddits = new ArrayList<>();
        this.votes = new ArrayList<>();*/
    }

    public void addNewMultiReddit(String name) {
        this.multiReddits.add(new MultiReddit(name, this));
    }

    public List<MultiReddit> getMultiReddits() {
        return this.multiReddits;
    }

    @Override
    public String toString() {
        return String.format(
                "Redditor[id=%d, username=%s, passWord=%s]",
                id, username, passWord
        );
    }
}
