package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.CommentController;
import danielrichtersz.models.*;
import danielrichtersz.models.enums.TypeVote;
import danielrichtersz.services.interfaces.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentControllerImpl implements CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private RedditorService redditorService;

    @Autowired
    private VoteService voteService;

    @PostMapping("/subreddits/{subredditname}/posts/{postid}/{posttitle}/comments")
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

    @PutMapping("/subreddits/{subredditname}/posts/{postid}/{posttitle}/comments/{commentid}")
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        if (comment.isDeleted()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This comment was deleted");
        }

        //TODO: Authorization
        //if (!comment.getOwner().getUsername().equals(username)) {
        //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the rights to edit this comment");
        //}

        Comment updatedComment = commentService.updateComment(commentId, content);

        if (updatedComment == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while updating the comment");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedComment);
    }

    @GetMapping("/comments/search")
    @Override
    public ResponseEntity searchForComment(
            @ApiParam(value = "The search term for finding a list of comments")
            @RequestParam(value = "searchterm") String searchterm) {
        List<Comment> foundComments = commentService.findComment(searchterm);

        if (foundComments == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No comments found containing the search term in their content");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(foundComments);
    }

    @GetMapping("/subreddits/{subredditname}/posts/{postid}/{posttitle}/comments/{commentid}")
    @Override
    public ResponseEntity getComment(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The id of the comment")
            @PathVariable(value = "commentid") Long commentId) {

        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The comment could not be found");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(comment);
    }

    @DeleteMapping("/redditors/{username}/posts/{postid}/{posttitle}/comments/{commentid}")
    @Override
    public ResponseEntity deleteComment(
            @ApiParam(value = "The id of the comment")
            @PathVariable(value = "commentid") Long commentId,
            @ApiParam(value = "The username of the redditor trying to delete the comment")
            @PathVariable(value = "username") String username) {

        Comment comment = commentService.findCommentById(commentId);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The comment could not be found");
        }

        if (comment.isDeleted()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This comment was already deleted");
        }

        //TODO: Authorization
        //if (!comment.getOwner().getUsername().equals(username)) {
        //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the rights to delete this comment");
        //}

        try {
            commentService.deleteComment(commentId);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Comment was removed");
    }

    @PostMapping("/subreddits/{subredditname}/posts/{postid}/{title}/comments/{commentid}/vote")
    @Override
    public ResponseEntity upvoteOrDownvoteComment(
            @ApiParam(value = "The username of the redditor")
            @RequestParam(value = "username") String username,
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "commentid") Long commentid,
            @ApiParam(value = "The type of vote: 'up' or 'down'")
            @RequestParam(value = "votetype") String voteType) {

        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        Comment comment = commentService.findCommentById(commentid);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        if (comment.isDeleted()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This comment is deleted, no votes can be cast upon it");
        }

        TypeVote typeVote;
        if (voteType.equals("up")) {
            typeVote = TypeVote.Upvote;
        }
        else if (voteType.equals("down")) {
            typeVote = TypeVote.Downvote;
        }
        else if (voteType.equals("none")) {
            typeVote = null;
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Problem casting vote");
        }

        //Updating vote or deleting vote? Only if the vote already exists
        if (voteService.getVote(commentid, username) != null) {
            if (typeVote == null) {
                voteService.deleteVote(commentid, username);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Vote removed");
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(voteService.updateOrCreateVote(commentid, username, typeVote));
        }

        //Creating new vote
        //Add to post and update post
        Vote createdVote = voteService.updateOrCreateVote(commentid, username, typeVote);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdVote);
    }
}
