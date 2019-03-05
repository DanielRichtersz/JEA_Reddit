package danielrichtersz.models;

import danielrichtersz.models.components.OwnerContainer;
import danielrichtersz.models.components.ReactionContainer;
import danielrichtersz.models.components.VoteContainer;


public class Comment extends ReactionContainer {
    private String comment;

    private ReactionContainer reactionContainer;
    private OwnerContainer ownerContainer;
    private VoteContainer voteContainer;


    public Comment(String comment, Redditor owner) {
        this.comment = comment;
        this.reactionContainer = new ReactionContainer();
        this.ownerContainer = new OwnerContainer(owner);
        this.voteContainer = new VoteContainer();
    }

    public ReactionContainer getReactionContainer() {
        return this.reactionContainer;
    }

    public OwnerContainer getOwnerContainer() {
        return this.ownerContainer;
    }

    public VoteContainer getVoteContainer() {
        return this.voteContainer;
    }

}