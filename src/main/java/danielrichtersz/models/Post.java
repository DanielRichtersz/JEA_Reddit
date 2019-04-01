package danielrichtersz.models;

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
}