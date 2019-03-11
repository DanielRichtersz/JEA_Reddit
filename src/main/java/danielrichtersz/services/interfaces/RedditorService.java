package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.RedditorRepository;

public interface RedditorService {
    Redditor createUser(String username, String password);
}
