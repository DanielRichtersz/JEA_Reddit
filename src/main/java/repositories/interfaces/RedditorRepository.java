package repositories.interfaces;

import models.Redditor;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RedditorRepository extends CrudRepository<Redditor, Long> {

    List<Redditor> findByUserName(String lastName);
}