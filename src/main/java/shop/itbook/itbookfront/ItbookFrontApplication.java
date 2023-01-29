package shop.itbook.itbookfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class ItbookFrontApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ItbookFrontApplication.class, args);
    }

}
