package danielrichtersz.repositories.interfaces;


import danielrichtersz.models.Post;
import danielrichtersz.models.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    //Post findById(Long postId);

    List<Post> findByTitleContainingOrContentContaining(String title, String content);

    @Query("select count(P.id) from Postable as P inner join Redditor as R " +
            "on P.owner = R.id and R.username = ?1")
    int getAmountOfPosts(String username);

    @Query("select p from Post p inner join Subreddit as S on p.subreddit = S.id and S.name = ?1")
    List<Post> getPostsFromSubredditFromIndexToIndex(String subredditName, int fromIndex, int toIndex);

    @Query("select p from Post p inner join Subreddit as S on p.subreddit = S.id and S.name = ?1 where p.title like %?2% or p.content like %?2% order by p.dateCreated desc")
    List<Post> findPostsFromSubredditBySearchTerm(String subredditName, String searchTerm);
}
