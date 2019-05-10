package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.CommentController;
import danielrichtersz.models.*;
import danielrichtersz.models.enums.TypeVote;
import danielrichtersz.services.interfaces.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@Api(value = "Comment Controller Implementation", description = "Operations pertaining to comments through Spring Boot REST API")
public class CommentControllerImpl implements CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private RedditorService redditorService;

    @Autowired
    private VoteService voteService;

    @PostMapping("/comments")
    @Override
    public ResponseEntity createComment(@ApiParam(value = "The name of the redditor")
                                            @RequestParam(value = "username") String username,
                                        @ApiParam(value = "The content of the comment")
                                            @RequestParam(value = "content") String content,
                                        @ApiParam(value = "The id of the postable to which the comment was made")
                                            @RequestParam(value = "postableId") Long postableId  ) {
        //No valid redditor
        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        Postable postable = commentService.findCommentById(postableId);
        if (postable == null) {
            postable = postService.findPostById(postableId);
            if (postable == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment or post not found");
            }
        }

        Comment comment = commentService.createComment(content, username, postableId);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
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

        Subreddit subreddit = subredditService.getByName(subredditName);
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

        return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
    }

    @PostMapping("/search/comments")
    @Override
    public ResponseEntity searchForComment(
            @ApiParam(value = "The search term for finding a list of comments")
            @RequestParam(value = "searchTerm") String searchterm) {
        List<Comment> foundComments = commentService.findComment(searchterm);

        if (foundComments == null) {
            foundComments = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(foundComments);
    }

    @GetMapping("/comments")
    @Override
    public ResponseEntity getComment(
            @ApiParam(value = "The id of the comment")
            @PathVariable(value = "commentid") Long commentId) {

        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The comment could not be found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @GetMapping("subreddits/{subredditname}/posts/{postid}/{posttitle}/comments")
    @Override
    public ResponseEntity getCommentsFromPost(
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "postid") Long postId)
    {
        Post post = postService.findPostById(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        List<Comment> fComments = commentService.findCommentsByPostableId(postId);
        if (fComments == null) {
            fComments = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(fComments);
    }

    @GetMapping("comments/{commentId}/comments")
    @Override
    public ResponseEntity getCommentsFromComment(
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "commentId") Long commentId)
    {
        Comment comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }

        List<Comment> fComments = commentService.findCommentsByPostableId(commentId);
        if (fComments == null) {
            fComments = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(fComments);
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

        return ResponseEntity.status(HttpStatus.OK).body("Comment was removed");
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
                return ResponseEntity.status(HttpStatus.OK).body("Vote removed");
            }
            return ResponseEntity.status(HttpStatus.OK).body(voteService.updateOrCreateVote(commentid, username, typeVote));
        }

        //Creating new vote
        //Add to post and update post
        Vote createdVote = voteService.updateOrCreateVote(commentid, username, typeVote);

        return ResponseEntity.status(HttpStatus.OK).body(createdVote);
    }
}
