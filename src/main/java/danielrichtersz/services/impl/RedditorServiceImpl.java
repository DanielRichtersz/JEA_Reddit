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

    @Override
    public Redditor createRedditor(String username, String password) {
        Redditor redditor = new Redditor(username, password);
        return redditorRepository.save(redditor);
    }

    @Override
    public void deleteRedditor(String username) {
        redditorRepository.deleteByUsername(username);
    }

    @Override
    public Redditor findById(Long redditorId) {
        return redditorRepository.findById(redditorId).orElse(null);
    }

    @Override
    public Redditor updateRedditor(String username, String newpassword) {
        Redditor redditor = redditorRepository.findByUsername(username);
        redditor.setPassword(newpassword);
        return redditorRepository.save(redditor);
    }
}
