package danielrichtersz.controllers.interfaces;

import danielrichtersz.models.Redditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface RedditorController {

    ResponseEntity getRedditorByUsername(@PathVariable(value = "name") String redditorUsername);

    ResponseEntity createRedditor(String username, String password);
}