package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.MultiReddit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MultiRedditRepository extends CrudRepository<MultiReddit, Long> {

    List<MultiReddit> findByName(String name);

}