package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.SubredditController;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.services.interfaces.PostService;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
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
@Api(value = "Subreddit Controller Implementation", description = "Operations pertaining to subreddits through Spring Boot REST API")
public class SubredditControllerImpl implements SubredditController {

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private RedditorService redditorService;

    @Autowired
    private PostService postService;

    @PostMapping("/subreddits")
    @Override
    public ResponseEntity createSubreddit(@RequestParam(name = "name") String subredditName,
                                          @RequestParam(name = "description") String description,
                                          @RequestParam(name = "username") String username) {
        if (subredditService.getByName(subredditName) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This subreddit already exists, try a different name");
        }

        if (redditorService.findByUsername(username) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not create subreddit, redditor not found");
        }

        Subreddit newSubreddit = subredditService.createSubreddit(subredditName, description, username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newSubreddit);
    }

    @PutMapping("/subreddits/{subredditname}")
    @Override
    public ResponseEntity editSubredditDescriptionBySubredditNameAndUsername(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The username of the redditor")
            @RequestParam(value = "username") String username,
            @ApiParam(value = "The new description of the subreddit")
            @RequestParam(value = "description") String description) {

        //The editing of the subreddit
        Subreddit subreddit = subredditService.getByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The subreddit you tried to edit does not exist");
        }

        if (redditorService.findByUsername(username) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No redditor with this name could be found");
        }

        for (Redditor moderator : subreddit.getModerators()) {
            if (moderator.getUsername().equals(username)) {
                Subreddit updatedSubreddit = subredditService.updateSubreddit(subredditName, description);

                if (updatedSubreddit == null) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Something went wrong while updating the subreddit, please try again or contact customer support");
                }
                return ResponseEntity.status(HttpStatus.OK).body(updatedSubreddit);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have the correct user rights to edit this subreddit");
    }

    @DeleteMapping("/subreddits")
    @Override
    public ResponseEntity deleteSubreddit(@ApiParam(value = "The name of the subreddit")
                                          @RequestParam(value = "subredditname") String subredditName,
                                          @ApiParam(value = "The username of the redditor")
                                          @RequestParam(value = "username") String username) {
        Subreddit subreddit = subredditService.getByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The subreddit you tried to delete does not exist");
        }

        if (redditorService.findByUsername(username) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No redditor with this name could be found");
        }

        for (Redditor moderator : subreddit.getModerators()) {
            if (moderator.getUsername().equals(username)) {
                boolean deleted = subredditService.deleteSubreddit(subredditName, username);

                if (deleted) {
                    return ResponseEntity.status(HttpStatus.OK).body("Subreddit was deleted");
                }
                else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while deleting the subreddit, please try again");
                }
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed to delete this subreddit");
    }

    @GetMapping("/subreddits/{subredditname}")
    @Override
    public ResponseEntity getSubredditByName(@ApiParam(value = "The name of the subreddit") @PathVariable(value = "subredditname") String subredditName) {
        Subreddit subreddit = subredditService.getByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subreddit with the given name exists");
        }
        return ResponseEntity.status(HttpStatus.OK).body(subreddit);
    }

    @GetMapping("/subreddits/{subredditname}/posts/{from}/to/{to}")
    @Override
    public ResponseEntity getSubredditPostsFromTo(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "Start index value of current top posts to get")
            @PathVariable(value = "from") int from,
            @ApiParam(value = "End index value of current top posts to get")
            @PathVariable(value = "to") int to) {
        Subreddit subreddit = subredditService.getByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subreddit with the given name exists");
        }

        List<Post> posts = postService.getSubredditPostsFromTo(from, to, subredditName);
        if (posts == null || posts.size() == 0) {
            posts = new ArrayList<>();
        }

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PostMapping("/subreddits/{subredditname}/subscribe")
    @Override
    public ResponseEntity subscribeToSubreddit(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The name of the redditor to subscribe to this subreddit")
            @RequestParam(name = "username") String username) {
        Subreddit subreddit = subredditService.getByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The subreddit you tried to delete does not exist");
        }

        if (redditorService.findByUsername(username) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No redditor with this name could be found");
        }

        if (this.subredditService.getFollowingSubreddit(username, subredditName)) {
            if (!this.subredditService.removeFollower(username, subredditName)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while unsubscribing from subreddit, please try again");
            }
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }

        if (!this.subredditService.addFollower(username, subredditName)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while subscribing to subreddit, please try again");
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/subreddits/{subredditname}/getsubscribed")
    @Override
    public ResponseEntity getSubscribedToSubreddit(
            @ApiParam(value = "The name of the subreddit")
            @PathVariable(value = "subredditname") String subredditName,
            @ApiParam(value = "The username of the redditor")
            @RequestParam(name = "username") String username) {
        Subreddit subreddit = subredditService.getByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The subreddit you tried to delete does not exist");
        }

        if (redditorService.findByUsername(username) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No redditor with this name could be found");
        }

        if (this.subredditService.getFollowingSubreddit(username, subredditName)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }

        return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @PostMapping("/search/subreddits")
    @Override
    public ResponseEntity findSubreddit(
            @ApiParam(value = "The searchterm to search for")
            @RequestParam(value = "searchTerm") String searchTerm) {
        List<Subreddit> subreddits = subredditService.findSubredditsByNameOrDescriptionContaining(searchTerm);

        if (subreddits == null || subreddits.size() == 0) {
            subreddits = new ArrayList<>();
        }
        return ResponseEntity.status(HttpStatus.OK).body(subreddits);
    }
}
