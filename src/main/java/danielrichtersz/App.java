package danielrichtersz;

import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.MultiRedditRepository;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.controllers.interfaces.RedditorController;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Autowired
    RedditorController redditorController;

    @Bean
    public CommandLineRunner demo(RedditorRepository repository, MultiRedditRepository multiRedditRepository, SubredditRepository subredditRepository, PostRepository postRepository) {
        return (args -> {
            log.info("Creating test redditors");
            log.info("------------------------------");
            // save couple redditors
            Redditor redditor1 = new Redditor("username1", "password1");
            Redditor redditor2 = new Redditor("username2", "password2");
            Redditor redditor3 = new Redditor("username3", "password3");
            Redditor redditor4 = new Redditor("username4", "password4");
            repository.save(redditor1);
            repository.save(redditor2);
            repository.save(redditor3);
            repository.save(redditor4);
            log.info("");

            log.info("Creating Food subreddit");
            log.info("------------------------------");
            Subreddit foodSubreddit = new Subreddit("Food", "All things food!", redditor4);
            subredditRepository.save(foodSubreddit);

            foodSubreddit.addFollower(redditor1);
            foodSubreddit.addFollower(redditor2);

            subredditRepository.save(foodSubreddit);
            log.info("");

            log.info("Creating Sports subreddit");
            log.info("------------------------------");
            Subreddit sportsSubreddit = new Subreddit("Sports", "All things sport!", redditor3);
            sportsSubreddit.addFollower(redditor1);
            sportsSubreddit.addFollower(redditor2);
            sportsSubreddit.addFollower(redditor3);
            subredditRepository.save(sportsSubreddit);
            log.info("");

            log.info("Creating Gaming subreddit");
            log.info("------------------------------");
            Subreddit gamingSubreddit = new Subreddit("Gaming", "All things gaming!", redditor1);
            gamingSubreddit.addFollower(redditor3);
            gamingSubreddit.addFollower(redditor4);
            subredditRepository.save(gamingSubreddit);
            log.info("");

            repository.save(redditor1);
            repository.save(redditor2);
            repository.save(redditor3);
            repository.save(redditor4);

            log.info("Creating test posts");
            log.info("------------------------------");
            Post sportsPost1 = new Post("Sports post 1 Title", "Sports post 1 Content", sportsSubreddit, redditor1);
            Thread.sleep(5000);
            Post sportsPost2 = new Post("Sports post 2 Title", "Sports post 2 Content", sportsSubreddit, redditor2);
            Thread.sleep(1000);
            Post sportsPost3 = new Post("Sports post 3 Title", "Sports post 3 Content", sportsSubreddit, redditor3);
            Thread.sleep(2000);
            Post sportsPost4 = new Post("Sports post 4 Title", "Sports post 4 Content", sportsSubreddit, redditor4);

            Thread.sleep(3000);
            Post foodPost1 = new Post("Food post 1 Title", "Food post 1 Content", foodSubreddit, redditor1);
            Thread.sleep(1000);
            Post foodPost2 = new Post("Food post 2 Title", "Food post 2 Content", foodSubreddit, redditor2);
            Thread.sleep(1000);
            Post foodPost3 = new Post("Food post 3 Title", "Food post 3 Content", foodSubreddit, redditor3);
            Thread.sleep(1000);
            Post foodPost4 = new Post("Food post 4 Title", "Food post 4 Content", foodSubreddit, redditor4);

            Thread.sleep(1000);
            Post gamingPost4 = new Post("Gaming post 4 Title", "Gaming post 4 Content", gamingSubreddit, redditor4);
            Thread.sleep(1000);
            Post gamingPost1 = new Post("Gaming post 1 Need for Speed Title", "Gaming post 1 Content", gamingSubreddit, redditor1);
            Thread.sleep(1000);
            Post gamingPost3 = new Post("Gaming post 3 Title", "Gaming Need for Speed post 3 Content", gamingSubreddit, redditor3);
            Thread.sleep(1000);
            Post gamingPost2 = new Post("Gaming post 2 Need for Speed Title", "Gaming Need for Speed post 2 Content", gamingSubreddit, redditor2);

            postRepository.save(sportsPost1);
            postRepository.save(sportsPost2);
            postRepository.save(sportsPost3);
            postRepository.save(sportsPost4);
            postRepository.save(foodPost1);
            postRepository.save(foodPost2);
            postRepository.save(foodPost3);
            postRepository.save(foodPost4);
            postRepository.save(gamingPost1);
            postRepository.save(gamingPost2);
            postRepository.save(gamingPost3);
            postRepository.save(gamingPost4);

            log.info("");

            // fetch all redditors
            log.info("Redditors found with findAll()");
            log.info("------------------------------");

            for (Redditor redditor : repository.findAll()) {
                log.info(redditor.toString());
            }
            log.info("");

            // fetch individual
            repository.findById(1L).ifPresent(individualRedditor -> {
                log.info("Redditor found by ID(1L):");
                log.info("-------------------------");
                log.info(individualRedditor.toString());
                log.info("");
            });

            // fetch by last name
            log.info("Redditor found with findByUserName('username2'):");
            log.info("--------------------------------------------");
            Redditor username1 = repository.findByUsername("username2");
            if (username1 != null) {
                log.info(username1.toString());
                username1.addNewMultiReddit("Timeline");
                log.info("Added new multi reddit");
                repository.save(username1);
            }
            log.info("");

            log.info("MultiReddits found with getByName:");
            log.info("---------------------------------");
            multiRedditRepository.findByName("Timeline").forEach(multiReddit -> {
                if (multiReddit != null) {
                    log.info("Found multireddit:");
                    log.info(multiReddit.toString());
                }
            });

            log.info("");

            log.info("Find subreddit");
            Subreddit subreddit = subredditRepository.getByName("TestSubreddit");
            if (subreddit != null) {
                log.info("Subreddit found: " + subreddit.toString());

                log.info("MultiReddits found with getByName:");
                log.info("---------------------------------");
                multiRedditRepository.findByName("Timeline").forEach(multiReddit -> {
                    if (multiReddit != null) {
                        log.info("Found multireddit:");
                        log.info(multiReddit.toString());
                        //multiReddit.addSubReddit(subreddit);
                        //multiRedditRepository.save(multiReddit);
                    }
                });
               // if (redditor != null) {
                 //   subreddit.addNewPost(new Post("title", "contenttest", username1));
                   // subredditRepository.save(subreddit);
               // }
            }
            log.info("---------------------");
            log.info("");




            log.info("-------------------------------------------------------------");
            log.info("USING SERVICE LAYER");
            log.info("-------------------------------------------------------------");
            log.info("");

            log.info("Service running");

        });
    }
}
