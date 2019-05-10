package danielrichtersz.repositories.interfaces;

import danielrichtersz.models.Comment;
import danielrichtersz.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment getById(Long id);

    List<Comment> findByContentContains(String content);

    List<Comment> findByParentPostableIdOrderByDateCreatedDesc(Long parentPostableId);
}
