package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.PostController;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.models.Vote;
import danielrichtersz.models.enums.TypeVote;
import danielrichtersz.services.interfaces.PostService;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
import danielrichtersz.services.interfaces.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@Controller
@RequestMapping("/api")
@Api(value = "Post Controller Implementation", description = "Operations pertaining to posts through Spring Boot REST API")
public class PostControllerImpl implements PostController {

    @Autowired
    private RedditorService redditorService;

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private PostService postService;

    @Autowired
    private VoteService voteService;

    @PostMapping("/subreddits/{subredditname}/posts")
    @Override
    public ResponseEntity createPost(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The name of the redditor")
            @RequestParam(value = "username") String username,
            @ApiParam(value = "The title of the new post")
            @RequestParam(value = "title") String title,
            @ApiParam(value = "The content of the post")
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

        //No valid title
        if (title.equals("") || title.equals("[deleted]") || content.equals("") || content.equals("[deleted]")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid title provided for the post, please provide a valid title");
        }

        Post post = postService.createPost(new Post(title, content, subreddit, redditor));

        subreddit.addNewPost(post);
        subredditService.updateSubreddit(subreddit);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/subreddits/{subredditname}/posts/{postid}/{posttitle}")
    @Override
    public ResponseEntity editPost(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The title of the post")
            @PathVariable(value = "posttitle") String title,
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "postid") Long postId,
            @ApiParam(value = "The new content of the post")
            @RequestParam(value = "content") String content,
            @ApiParam(value = "The username of the redditor trying to edit the post")
            @RequestParam(value = "username") String username) {

        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        Post post = postService.findPostById(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        if (post.isDeleted()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This post was deleted");
        }

        //TODO: Authorization
        //if (!post.getOwner().getUsername().equals(username)) {
        //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the rights to edit this post");
        //}

        post.setContent(content);
        Post updatedPost = postService.updatePost(post);

        if (updatedPost == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while updating the post");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedPost);
    }

    @GetMapping("/posts/search")
    @Override
    public ResponseEntity searchForPost(
            @ApiParam(value = "The search term for finding a list of posts")
            @RequestParam(value = "searchterm") String searchterm) {
        List<Post> foundPosts = postService.findPost(searchterm);

        if (foundPosts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No posts found containing the search term in their title or content");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(foundPosts);
    }

    @GetMapping("/subreddits/{subredditname}/posts/{postid}/{posttitle}")
    @Override
    public ResponseEntity getPost(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "postid") Long postId,
            @ApiParam(value = "The title of the post")
            @PathVariable(value = "posttitle") String postTitle) {

        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        Post post = postService.findPostById(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post could not be found");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(post);
    }

    @DeleteMapping("/redditors/{username}/posts/{postid}")
    @Override
    public ResponseEntity deletePost(
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "postid") Long postId,
            @ApiParam(value = "The username of the redditor trying to delete the post")
            @PathVariable(value = "username") String username) {

        Post post = postService.findPostById(postId);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post could not be found");
        }

        if (post.isDeleted()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This post was already deleted");
        }

        //TODO: Authorization
        //if (!post.getOwner().getUsername().equals(username)) {
        //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the rights to delete this post");
        //}

        try {
            postService.deletePost(post);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Post was removed");
    }

    @PostMapping("/subreddits/{subredditname}/posts/{postid}/{title}/vote")
    @Override
    public ResponseEntity upvoteOrDownvotePost(
            @ApiParam(value = "The username of the redditor")
            @RequestParam(value = "username") String username,
            @ApiParam(value = "The id of the post")
            @PathVariable(value = "postid") Long postId,
            @ApiParam(value = "The type of vote: 'up' or 'down'")
            @RequestParam(value = "votetype") String voteType) {

        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        Post post = postService.findPostById(postId);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        if (post.isDeleted()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This post is deleted, no votes can be cast upon it");
        }

        //Existing vote?
        Vote vote = voteService.getVote(post, redditor);

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

        //Updating vote or deleting vote?
        //Update vote directly
        if (vote != null) {
            if (typeVote == null) {
                voteService.deleteVote(vote);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Vote removed");
            }
            vote.setTypeVote(typeVote);
            voteService.updateVote(vote);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(voteService.updateVote(vote));
        }

        //Creating new vote
        //Add to post and update post
        Vote createdVote = voteService.createVote(new Vote(post, redditor, typeVote));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdVote);
    }
}
