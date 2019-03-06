package danielrichtersz.models;

import danielrichtersz.models.components.OwnerContainer;
import danielrichtersz.models.components.ReactionContainer;
import danielrichtersz.models.components.VoteContainer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @OneToOne
    private ReactionContainer reactionContainer;

    @OneToOne
    private OwnerContainer ownerContainer;

    @OneToOne
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