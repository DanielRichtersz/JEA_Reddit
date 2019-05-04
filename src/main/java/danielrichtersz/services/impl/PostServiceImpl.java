package danielrichtersz.services.impl;

import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import danielrichtersz.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RedditorRepository redditorRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    @Override
    public Post createPost(String title, String content, String subredditName, String username) {
        Redditor redditor = redditorRepository.findByUsername(username);
        Subreddit subreddit = subredditRepository.getByName(subredditName);
        return postRepository.save(new Post(title, content, subreddit, redditor));
    }

    @Override
    public Post updatePost(Long postId, String newContent) {
        Post post = postRepository.findById(postId).orElse(null);
        postRepository.findById(postId);

        if (post == null) {
            return null;
        }
        post.setContent(newContent);
        return postRepository.save(post);
    }

    @Override
    public List<Post> findPosts(String searchTerm) {
        return postRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).get();
        post.delete();
        postRepository.save(post);
    }

    @Override
    public String getPostTitleFromUrl(String urlTitle) {
        return urlTitle.replace('_', ' ');
    }

    @Override
    public int getAmountOfPosts(String username) {
        return this.postRepository.getAmountOfPosts(username);
    }

    @Override
    public List<Post> getSubredditPostsFromTo(int from, int to, String subredditName) {
        try {
            //TODO: Actual results on indexed so you can get by 10
            return postRepository.getPostsFromSubredditFromIndexToIndex(subredditName, 0, 2);
                    //getPostsFromSubredditFromIndexToIndex(subredditName, from, to);
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
