package danielrichtersz.models.components;

import danielrichtersz.models.Comment;

import java.util.List;

public class ReactionContainer {

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
