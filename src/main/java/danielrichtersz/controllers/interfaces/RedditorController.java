package danielrichtersz.controllers.interfaces;

import danielrichtersz.models.Redditor;

public interface RedditorController {
    Redditor createUser(String username, String password);
}
