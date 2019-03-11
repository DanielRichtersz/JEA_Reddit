package danielrichtersz.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Redditor> moderators;

    @OneToMany
    private List<Redditor> followers;

    public Subreddit() {

    }

    public Subreddit(String name) {
        this.name = name;
    }
}
