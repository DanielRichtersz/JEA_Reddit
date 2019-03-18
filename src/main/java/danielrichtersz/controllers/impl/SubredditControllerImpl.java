package danielrichtersz.controllers.impl;

import danielrichtersz.controllers.interfaces.SubredditController;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.services.interfaces.RedditorService;
import danielrichtersz.services.interfaces.SubredditService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity CreateSubreddit(@RequestParam(name = "name") String subredditName,
                                          @RequestParam(name = "description") String description,
                                          @RequestParam(name = "userID") Long redditorId) {
        if (subredditService.findByName(subredditName) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This subreddit already exists, try a different name");
        }

        Redditor owner = redditorService.findById(redditorId);

        if (owner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redditor not found");
        }

        Subreddit newSubreddit = subredditService.createSubreddit(subredditName, description, owner);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newSubreddit);
    }
}
