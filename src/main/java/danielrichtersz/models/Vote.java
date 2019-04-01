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
    private Postable postable;

    public Vote() {

    }

    public Vote(Postable postable, Redditor owner, TypeVote typeVote) {
        this.postable = postable;
        this.typeVote = typeVote;
        this.owner = owner;
    }

    public TypeVote getTypeVote() {
        return this.typeVote;
    }

    public Redditor getOwner() {
        return this.owner;
    }

    public Postable getPostable() {
        return this.postable;
    }

    public void setTypeVote(TypeVote typeVote) {
        this.typeVote = typeVote;
    }
}
