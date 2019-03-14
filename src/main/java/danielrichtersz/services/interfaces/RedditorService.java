package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;

public interface RedditorService {

    Redditor findByUsername(String username);

    Redditor createRedditor(String username, String password);

    void deleteRedditor(Long id);
}
