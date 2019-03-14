package danielrichtersz.controllers.interfaces;

import danielrichtersz.models.Redditor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface RedditorController {

    Redditor getRedditorsByName(@PathVariable(value = "name") String redditorUsername);

    Redditor createRedditor(String username, String password);
}