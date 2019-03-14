package danielrichtersz.controllers.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.controllers.interfaces.RedditorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//Controller is same as Bean, endpoint layer
@Controller
@RequestMapping("/api")
public class RedditorControllerImpl implements RedditorController {

    public RedditorControllerImpl() {

    }

    public RedditorControllerImpl(RedditorRepository redditorRepository) {
        this.redditorRepository = redditorRepository;
    }


    @ResponseBody
    @GetMapping("/redditors/{name}")
    public Redditor getRedditorsByName(@PathVariable(value = "name") String redditorUsername) {
        Redditor found = redditorRepository.findByUsername(redditorUsername);
        return found;
    }

    @PostMapping("/redditors")
    public Redditor createRedditor(@Valid @RequestBody Redditor redditor) {
        return redditorRepository.save(redditor);
    }

    @Override
    public Redditor createUser(String username, String password) {
        Redditor redditor = new Redditor(username, password);
        redditorRepository.save(redditor);
        return redditor;
    }
}
