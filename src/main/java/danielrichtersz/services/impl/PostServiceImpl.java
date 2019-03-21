package danielrichtersz.services.impl;

import danielrichtersz.models.Post;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findByPostTitleAndOwnerUsername(String title, String username) {
        return postRepository.findByTitleAndOwnerUsername(title, username);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }


}
