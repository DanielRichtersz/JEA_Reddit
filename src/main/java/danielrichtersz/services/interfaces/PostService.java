package danielrichtersz.services.interfaces;

import danielrichtersz.models.Post;
import danielrichtersz.models.Subreddit;

import java.util.List;

public interface PostService {
    Post createPost(Post post);

    Post updatePost(Post post);

    List<Post> findPost(String searchTerm);

    Post findPostById(Long postId);

    void deletePost(Post post);
}
