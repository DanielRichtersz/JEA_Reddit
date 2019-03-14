package danielrichtersz;

import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.MultiRedditRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.controllers.interfaces.RedditorController;
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
    public CommandLineRunner demo(RedditorRepository repository, MultiRedditRepository multiRedditRepository) {
        return (args -> {
            // save couple redditors
            repository.save(new Redditor("username1", "password1"));
            repository.save(new Redditor("username2", "password2"));
            repository.save(new Redditor("username3", "password3"));
            repository.save(new Redditor("username4", "password4"));


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

            log.info("-------------------------------------------------------------");
            log.info("USING SERVICE LAYER");
            log.info("-------------------------------------------------------------");
            log.info("");

            log.info("Creating new Redditor with RedditorControllerImpl.createRedditor");
            Redditor redditor = redditorController.createRedditor("name", "password");
            log.info("-------------------------------------------------------------");
            log.info("Redditor created: " + redditor.toString());
            log.info("");

        });
    }
}
