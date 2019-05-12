package danielrichtersz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class Post extends Postable {

    private String title;

    @ManyToOne
    private Subreddit subreddit;

    public Post() {

    }

    public Post(String title, String content, Subreddit subreddit, Redditor owner) {
        super(content, owner);
        this.subreddit = subreddit;
        this.title = title;
    }


    public String getTitle() {
        return this.title;
    }

    public String getSubredditName() {
        return this.subreddit.getName();
    }
}