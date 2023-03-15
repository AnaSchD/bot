package skypro.com.an_sch_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnSchBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnSchBotApplication.class, args);
    }

}
