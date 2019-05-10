package danielrichtersz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@DiscriminatorValue("0")
public class Comment extends Postable{

@JsonIgnoreProperties("comments")
    @ManyToOne
    private Postable parentPostable;

    public Comment() {

    }

    public Comment(String content, Redditor owner, Postable parentPostable) {
        super(content, owner);
        this.parentPostable = parentPostable;
    }

    public Postable getParentPostable() {
        return this.parentPostable;
    }
}