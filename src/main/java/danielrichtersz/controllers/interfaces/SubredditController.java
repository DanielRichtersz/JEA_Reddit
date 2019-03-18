package danielrichtersz.controllers.interfaces;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
