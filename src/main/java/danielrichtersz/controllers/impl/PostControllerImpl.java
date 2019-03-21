package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.PostController;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.services.interfaces.PostService;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class PostControllerImpl implements PostController {

    @Autowired
    private RedditorService redditorService;

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private PostService postService;

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

        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        if (title.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No title provided for the post, please provide a title");
        }

        Post post = new Post(title, content, subreddit, redditor);
        postService.createPost(post);

        subreddit.addNewPost(post);
        subredditService.updateSubreddit(subreddit);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/subreddits/{subredditname}/posts")
    @Override
    public ResponseEntity editPost(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The username of the redditor, for user rights validation")
            @RequestParam(value = "username") String username,
            @ApiParam(value = "The new content of the post")
            @RequestParam(value = "content") String content,
            @ApiParam(value = "The title of the post")
            @RequestParam(value = "title") String title) {

        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subreddit not found");
        }

        Redditor redditor = redditorService.findByUsername(username);
        if (redditor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        Post post = postService.findByPostTitleAndOwnerUsername(title, username);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        if (!post.getOwner().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the rights to edit this post");
        }

        post.setContent(content);
        Post updatedPost = postService.updatePost(post);

        if (updatedPost == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while updating the post");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedPost);
    }


}
