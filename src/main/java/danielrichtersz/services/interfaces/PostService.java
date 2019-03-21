package danielrichtersz.services.interfaces;

import danielrichtersz.models.Post;

public interface PostService {
    Post createPost(Post post);

    Post findByPostTitleAndOwnerUsername(String title, String username);

    Post updatePost(Post post);
}
