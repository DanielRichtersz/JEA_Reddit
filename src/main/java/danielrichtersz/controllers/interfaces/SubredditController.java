package danielrichtersz.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface SubredditController {
    ResponseEntity CreateSubreddit(String subredditName,
                                   String description,
                                   Long redditorId);
}
