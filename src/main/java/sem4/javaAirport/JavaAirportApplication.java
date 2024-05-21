package sem4.javaAirport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JavaAirportApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAirportApplication.class, args);
    }

}
