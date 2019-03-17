package danielrichtersz.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Redditor> moderators;

    @OneToMany
    private List<Redditor> followers;

    public Subreddit() {

    }

    public Subreddit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public List<Redditor> getModerators() {
        return this.moderators;
    }

    public List<Redditor> getFollowers() {
        return this.followers;
    }

    public void addNewPost(Post post) {
        this.posts.add(post);
    }
}
