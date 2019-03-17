package danielrichtersz.repositories.interfaces;


import danielrichtersz.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
