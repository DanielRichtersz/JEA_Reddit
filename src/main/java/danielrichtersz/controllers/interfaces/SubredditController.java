package danielrichtersz.controllers.interfaces;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

public interface SubredditController {
    ResponseEntity createSubreddit(String subredditName,
                                   String description,
                                   String username);

    ResponseEntity getSubredditByName(String subredditName);

    ResponseEntity editSubredditDescriptionBySubredditNameAndUsername(
            String subredditName,
            String username,
            String description);

    @DeleteMapping("/subreddits")
    ResponseEntity deleteSubreddit(String subredditName, String username);
}
