package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;

public interface RedditorService {

    Redditor findByUsername(String username);

    Redditor createRedditor(String username, String password);

    Redditor editRedditor(Redditor redditor);

    void deleteRedditor(Long id);

    Redditor findById(Long redditorId);
}
