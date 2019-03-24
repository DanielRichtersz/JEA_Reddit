package danielrichtersz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "subreddit", orphanRemoval = true)
    private List<Post> posts;

    @OneToMany (fetch = FetchType.LAZY)
    private List<Redditor> moderators;

    @OneToMany
    private List<Redditor> followers;

    public Subreddit() {

    }

    public Subreddit(String name, String description, Redditor owner) {
        this.name = name;
        this.description = description;
        this.posts = new ArrayList<>();
        this.moderators = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.moderators.add(owner);
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

    public void setDescription(String description) {
        this.description = description;
    }
}
