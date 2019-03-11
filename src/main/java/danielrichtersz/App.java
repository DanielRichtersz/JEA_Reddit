package danielrichtersz;

import danielrichtersz.models.MultiReddit;
import danielrichtersz.models.Redditor;
import danielrichtersz.repositories.interfaces.MultiRedditRepository;
import danielrichtersz.repositories.interfaces.RedditorRepository;
import danielrichtersz.services.impl.RedditorServiceImpl;
import danielrichtersz.services.interfaces.RedditorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Autowired
    RedditorService redditorService;

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
            log.info("Customer found with findByUserName('username2'):");
            log.info("--------------------------------------------");
            repository.findByUserName("username2").forEach(username1 -> {
                log.info(username1.toString());
                username1.addNewMultiReddit("Timeline");
                log.info("Added new multi reddit");
                repository.save(username1);
            });
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

            log.info("Creating new Redditor with RedditorServiceImpl.createRedditor");
            Redditor redditor = redditorService.createUser("name", "password");
            log.info("-------------------------------------------------------------");
            log.info("Redditor created: " + redditor.toString());
            log.info("");

        });
    }
}
