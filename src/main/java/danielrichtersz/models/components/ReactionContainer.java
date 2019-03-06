package danielrichtersz.models.components;

import danielrichtersz.models.Comment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ReactionContainer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Comment> comments;

    public ReactionContainer() {

    }

    public List<Comment> getComments() {
        return this.comments;
    }
    public void addReaction(Comment comment) {
        comments.add(comment);
    }
}
