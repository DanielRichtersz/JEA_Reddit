package danielrichtersz;

import danielrichtersz.models.MultiReddit;
import danielrichtersz.models.Post;
import danielrichtersz.models.Redditor;
import danielrichtersz.models.Subreddit;
import danielrichtersz.repositories.interfaces.MultiRedditRepository;
import danielrichtersz.repositories.interfaces.PostRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.controllers.interfaces.RedditorController;
import danielrichtersz.repositories.interfaces.SubredditRepository;
import danielrichtersz.services.interfaces.SubredditService;
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
            // save couple redditors
            Redditor redditor1 = new Redditor("username1", "password1");
            Redditor redditor2 = new Redditor("username2", "password2");
            Redditor redditor3 = new Redditor("username3", "password3");
            Redditor redditor4 = new Redditor("username4", "password4");
            repository.save(redditor1);
            repository.save(redditor2);
            repository.save(redditor3);
            repository.save(redditor4);


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

            log.info("MultiReddits found with findByName:");
            log.info("---------------------------------");
            multiRedditRepository.findByName("Timeline").forEach(multiReddit -> {
                if (multiReddit != null) {
                    log.info("Found multireddit:");
                    log.info(multiReddit.toString());
                }
            });

            log.info("");

            log.info("Find subreddit");
            Subreddit subreddit = subredditRepository.findByName("TestSubreddit");
            if (subreddit != null) {
                log.info("Subreddit found: " + subreddit.toString());

                log.info("MultiReddits found with findByName:");
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


        });
    }
}
