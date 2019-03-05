package application;

import models.Redditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import repositories.interfaces.RedditorRepository;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Bean
    public CommandLineRunner demo(RedditorRepository repository) {
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
            log.info("Customer found with findByUserName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByUserName("username1").forEach(username1 -> {
                log.info(username1.toString());
            });
            log.info("");

        });
    }
}
