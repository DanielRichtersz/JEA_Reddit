package danielrichtersz.services.interfaces;

import danielrichtersz.models.Redditor;

public interface RedditorService {

    Redditor findByUsername(String username);
}
