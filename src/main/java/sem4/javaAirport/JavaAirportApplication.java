package sem4.javaAirport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javafx.application.Application;
import org.springframework.context.ConfigurableApplicationContext;
import sem4.javaAirport.business.impl.BaggageService;

@SpringBootApplication
public class JavaAirportApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JavaAirportApplication.class, args);
        BaggageService baggageService = context.getBean(BaggageService.class);
        baggageService.testUpdateBaggageStatus();
    }

}
