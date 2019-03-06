package danielrichtersz.models.components;

import danielrichtersz.models.Vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class VoteContainer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Vote> votes;

    public VoteContainer() {
        this.votes = new ArrayList<>();
    }

    public List<Vote> getVotes() {
        return this.votes;
    }
}