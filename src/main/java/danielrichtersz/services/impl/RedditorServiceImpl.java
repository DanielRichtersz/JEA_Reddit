package danielrichtersz.services.impl;

import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.services.interfaces.RedditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedditorServiceImpl implements RedditorService {

    @Autowired
    private RedditorRepository redditorRepository;


    @Override
    public Redditor findByUsername(String username) {
        return redditorRepository.findByUsername(username);
    }
}
