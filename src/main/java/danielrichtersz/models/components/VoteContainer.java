package danielrichtersz.models.components;

import danielrichtersz.models.Vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Deprecated
//@Entity
public class VoteContainer {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Vote> votes;

    public VoteContainer() {

    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }
}