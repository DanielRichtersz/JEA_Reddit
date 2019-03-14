package danielrichtersz.controllers.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.controllers.interfaces.RedditorController;
import danielrichtersz.services.interfaces.RedditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//Controller is same as Bean, endpoint layer
@Controller
@RequestMapping("/api")
public class RedditorControllerImpl implements RedditorController {

    @Autowired
    RedditorService redditorService;

    public RedditorControllerImpl() {

    }

    public RedditorControllerImpl(RedditorService redditorService) {
        this.redditorService = redditorService;
    }


    @ResponseBody
    @GetMapping("/redditors/{name}")
    public Redditor getRedditorsByName(@PathVariable(value = "name") String redditorUsername) {
        Redditor found = redditorService.findByUsername(redditorUsername);
        return found;
    }

    @PostMapping("/redditors/{name}")
    public Redditor createRedditor(String username, String password) {
        return redditorService.createRedditor(username, password);
    }
}
