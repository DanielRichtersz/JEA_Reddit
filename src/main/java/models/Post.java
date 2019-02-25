package models;

import models.components.OwnerContainer;
import models.components.ReactionContainer;
import models.components.VoteContainer;

import javax.persistence.Entity;

@Entity
public class Post {

    private String content;
    private ReactionContainer reactionContainer;
    private OwnerContainer ownerContainer;
    private VoteContainer voteContainer;

    public Post() {

    }

    public Post(String content, Redditor owner) {
        this.content = content;
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