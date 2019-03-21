package danielrichtersz.repositories.interfaces;


import danielrichtersz.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
}
