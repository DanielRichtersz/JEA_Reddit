package danielrichtersz.models;

import danielrichtersz.models.components.OwnerContainer;
import danielrichtersz.models.enums.TypeVote;

public class Vote {
    private Post post;
    private TypeVote typeVote;
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
