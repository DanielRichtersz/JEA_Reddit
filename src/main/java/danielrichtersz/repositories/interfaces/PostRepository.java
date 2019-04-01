package danielrichtersz.repositories.interfaces;


import danielrichtersz.models.Post;
import danielrichtersz.models.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    //Post findById(Long postId);

    List<Post> findByTitleContainingOrContentContaining(String title, String content);
}
