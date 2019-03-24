package danielrichtersz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import danielrichtersz.models.enums.TypeVote;

import javax.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    private TypeVote typeVote;

    @ManyToOne
    @JsonIgnore
    private Redditor owner;

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JsonIgnore
    private Comment comment;

    public Vote() {

    }

    public Vote(Post post, Redditor owner, TypeVote typeVote) {
        this.post = post;
        this.typeVote = typeVote;
        this.owner = owner;
    }

    public Vote(Comment comment, Redditor owner, TypeVote typeVote) {
        this.comment = comment;
        this.typeVote = typeVote;
        this.owner = owner;
    }

    public TypeVote getTypeVote() {
        return this.typeVote;
    }

    public Redditor getOwner() {
        return this.owner;
    }

    public Post getPost() {
        return this.post;
    }

    public Comment getComment() {
        return this.comment;
    }

    public void setTypeVote(TypeVote typeVote) {
        this.typeVote = typeVote;
    }
}
