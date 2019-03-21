package danielrichtersz.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    private Redditor owner;

    @ManyToOne
    private Subreddit subreddit;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Vote> votes;

    public Post() {

    }

    public Post(String title, String content, Subreddit subreddit,Redditor owner) {
        this.subreddit = subreddit;
        this.owner = owner;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Redditor getOwner() {
        return this.owner;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

}