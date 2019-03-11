package danielrichtersz.services.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.services.interfaces.RedditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedditorServiceImpl implements RedditorService {

    private RedditorRepository redditorRepository;

    public RedditorServiceImpl() {

    }

    @Autowired
    public RedditorServiceImpl(RedditorRepository redditorRepository) {
        this.redditorRepository = redditorRepository;
    }

    @Override
    public Redditor createUser(String username, String password) {
        Redditor redditor = new Redditor(username, password);
        redditorRepository.save(redditor);
        return redditor;
    }
}
