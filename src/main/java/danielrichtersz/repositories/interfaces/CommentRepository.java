package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Post, Long> {
    Post getById(Long id);
}
