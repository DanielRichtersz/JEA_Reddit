package danielrichtersz.services.interfaces;

import danielrichtersz.models.Post;
import danielrichtersz.models.Subreddit;

import java.util.List;

public interface PostService {
    Post createPost(String title, String content, String subredditName, String username);

    Post updatePost(Long postId, String newContent);

    List<Post> findPost(String searchTerm);

    Post findPostById(Long postId);

    void deletePost(Long postId);
}
