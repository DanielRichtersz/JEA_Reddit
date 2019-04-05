package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.CommentController;
import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.services.interfaces.CommentService;
import danielrichtersz.services.interfaces.PostService;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentControllerImpl implements CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private RedditorService redditorService;

    @Autowired
    private PostService postService;

    @Override
    public ResponseEntity createComment(@ApiParam(value = "The name of the subreddit")
                                            @PathVariable(value = "subredditname") String subredditName,
                                        @ApiParam(value = "The name of the redditor")
                                            @RequestParam(value = "username") String username,
                                        @ApiParam(value = "The content of the comment")
                                            @RequestParam(value = "content") String content) {

        //No valid subreddit in path
        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        //No valid redditor
        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        Comment comment = commentService.createComment(content, subredditName, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Override
    public ResponseEntity editComment(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The id of the comment")
            @PathVariable(value = "commentid") Long commentId,
            @ApiParam(value = "The new content of the comment")
            @RequestParam(value = "content") String content,
            @ApiParam(value = "The username of the redditor trying to edit the comment")
            @RequestParam(value = "username") String username) {

        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        //if (comment.isDeleted()) {
          //  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This post was deleted");
        //}

        //TODO: Authorization
        //if (!post.getOwner().getUsername().equals(username)) {
        //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the rights to edit this post");
        //}

        Comment updatedComment = commentService.updateComment(commentId, content);

        if (updatedComment == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while updating the comment");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedComment);
    }

    @Override
    public ResponseEntity searchForComment(String title) {
        return null;
    }

    @Override
    public ResponseEntity getComment(String subredditName, Long postId) {
        return null;
    }

    @Override
    public ResponseEntity deleteComment(Long commentId, String username) {
        return null;
    }

    @Override
    public ResponseEntity upvoteOrDownvoteComment(String username, Long commentId, String voteType) {
        return null;
    }
}
