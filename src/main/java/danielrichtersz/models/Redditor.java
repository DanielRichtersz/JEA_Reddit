package danielrichtersz.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Redditor {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String passWord;
    /**
     * MultiReddit should always contain 1 multiReddit which is the TimeLine of the user
     * The TimeLine multireddit contains all the subreddits the user follows in one collection
     */
    /*private List<MultiReddit> multiReddits;

    private List<Post> posts;

    private List<Comment> comments;

    private List<Vote> votes;*/


    public Redditor() {

    }

    public Redditor(String username, String password) {
        this.userName = username;
        this.passWord = password;
        /*this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.multiReddits = new ArrayList<>();
        this.votes = new ArrayList<>();*/
    }

    @Override
    public String toString() {
        return String.format(
                "Redditor[id=%d, userName=%s, passWord=%s]",
                id, userName, passWord
        );
    }
}
