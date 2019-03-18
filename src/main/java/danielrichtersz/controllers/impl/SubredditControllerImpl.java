package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.SubredditController;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/api")
@Api(value = "Subreddit Controller Implementation", description = "Operations pertaining to subreddits through Spring Boot REST API")
public class SubredditControllerImpl implements SubredditController {

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private RedditorService redditorService;

    @PostMapping("/subreddits")
    @Override
    public ResponseEntity createSubreddit(@RequestParam(name = "name") String subredditName,
                                          @RequestParam(name = "description") String description,
                                          @RequestParam(name = "username") String username) {
        if (subredditService.findByName(subredditName) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This subreddit already exists, try a different name");
        }

        Redditor owner = redditorService.findByUsername(username);

        if (owner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not create subreddit, redditor not found");
        }

        Subreddit newSubreddit = subredditService.createSubreddit(subredditName, description, owner);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newSubreddit);
    }

    @GetMapping("/subreddits/{subredditname}")
    @Override
    public ResponseEntity getSubredditByName(@ApiParam(value = "The name of the subreddit") @PathVariable(value = "subredditname") String subredditName) {
        Subreddit subreddit = subredditService.findByName(subredditName);
        if (subreddit == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subreddit with the given name exists");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(subreddit);
    }

}
