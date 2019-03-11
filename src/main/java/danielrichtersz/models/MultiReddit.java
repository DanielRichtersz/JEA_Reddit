package danielrichtersz.models;

import danielrichtersz.models.components.OwnerContainer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MultiReddit {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne (fetch = FetchType.EAGER)
    private Redditor redditor;

    @OneToMany
    private List<Subreddit> subreddits;

    public MultiReddit() {

    }

    public MultiReddit(String name, Redditor redditor) {
        this.redditor = redditor;
        this.name = name;
    }

    public void editName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void addSubReddit(Subreddit subreddit) {
        this.subreddits.add(subreddit);
    }

    public void removeSubreddit(Subreddit subreddit) {
        this.subreddits.remove(subreddit);
    }

    @Override
    public String toString() {
        return String.format("MultiReddit[id=%d, name=%s, redditor=%s]", id, name, redditor.toString());
    }
}
