package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;

public interface RedditorService {

    Redditor findByUsername(String username);

    Redditor createRedditor(String username, String password);

    Redditor editRedditor(Redditor redditor);

    void deleteRedditor(String username);

    Redditor findById(Long redditorId);
}
