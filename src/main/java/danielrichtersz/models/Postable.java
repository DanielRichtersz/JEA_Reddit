package danielrichtersz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "postable_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class Postable {
    @Id
    @GeneratedValue
    private Long id;

    private String content;
    private boolean deleted;
    private Date dateCreated;

    @ManyToOne
    private Redditor owner;

    @JsonIgnoreProperties("parentPostable")
    @OneToMany(mappedBy = "parentPostable")
    private List<Comment> comments;

    @OneToMany(mappedBy = "postable")
    private List<Vote> votes;

    public Postable() {

    }

    public Postable(String content, Redditor owner) {
        this.content = content;
        this.deleted = false;
        this.owner = owner;
        this.comments = new ArrayList<>();
        this.votes = new ArrayList<>();
        this.dateCreated = new Date();
    }

    public Redditor getOwner() {
        return this.owner;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public Long getId() {
        return this.id;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.content = "[deleted]";
        this.deleted = true;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
