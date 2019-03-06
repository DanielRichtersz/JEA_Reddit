package danielrichtersz.models;

import danielrichtersz.models.components.OwnerContainer;
import danielrichtersz.models.enums.TypeVote;

import javax.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Post post;
    private TypeVote typeVote;

    @OneToOne
    private OwnerContainer ownerContainer;

    public Vote(Redditor owner, Post post, TypeVote typeVote) {
        this.post = post;
        this.typeVote = typeVote;
        this.ownerContainer = new OwnerContainer(owner);
    }

    public TypeVote getVoteType() {
        return this.typeVote;
    }

    public OwnerContainer getOwnerContainer() {
        return this.ownerContainer;
    }
}
