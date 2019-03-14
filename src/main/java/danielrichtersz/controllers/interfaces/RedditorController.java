package danielrichtersz.controllers.interfaces;

import danielrichtersz.models.Redditor;
import org.springframework.web.bind.annotation.PostMapping;

public interface RedditorController {

    @PostMapping("/redditors/{name}")
    Redditor createRedditor(String username, String password);
}
