package danielrichtersz.models;

import danielrichtersz.models.enums.TypeVote;

import javax.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    private TypeVote typeVote;

    @ManyToOne
    private Redditor owner;

    public Vote() {

    }

    public Vote(Redditor owner, TypeVote typeVote) {
        this.typeVote = typeVote;
        this.owner = owner;
    }

    public TypeVote getTypeVote() {
        return this.typeVote;
    }

    public Redditor getOwner() {
        return this.owner;
    }
}
