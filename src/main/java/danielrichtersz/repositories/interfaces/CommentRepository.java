package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment getById(Long id);
}
