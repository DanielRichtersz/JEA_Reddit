package danielrichtersz.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class MultiReddit {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne (fetch = FetchType.EAGER)
    private Redditor owner;

    @OneToMany
    private List<Subreddit> subreddits;

    public MultiReddit() {

    }

    public MultiReddit(String name, Redditor owner) {
        this.owner = owner;
        this.name = name;
    }

    public void editName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public List<Subreddit> getSubreddits() {
        return this.subreddits;
    }

    public void addSubReddit(Subreddit subreddit) {
        this.subreddits.add(subreddit);
    }

    public void removeSubreddit(Subreddit subreddit) {
        this.subreddits.remove(subreddit);
    }

    @Override
    public String toString() {
        return String.format("MultiReddit[id=%d, name=%s, owner=%s]", id, name, owner.toString());
    }
}
