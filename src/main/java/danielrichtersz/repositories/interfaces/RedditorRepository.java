package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.MultiReddit;
import danielrichtersz.models.Redditor;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RedditorRepository extends CrudRepository<Redditor, Long> {

    Redditor findByUsername(String username);

    void deleteById(Long id);
}