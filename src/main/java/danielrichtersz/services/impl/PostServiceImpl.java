package danielrichtersz.services.impl;

import danielrichtersz.models.Post;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> findPost(String searchTerm) {
        return postRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public void deletePost(Post post) {
        post.delete();
        postRepository.save(post);
    }
}
