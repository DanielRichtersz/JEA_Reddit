package danielrichtersz.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @ManyToOne
    private Redditor owner;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Vote> votes;

    public Comment() {

    }

    public Comment(String comment, Redditor owner) {
        this.owner = owner;
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }
}