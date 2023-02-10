package shop.itbook.itbookfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@ConfigurationPropertiesScan
@EnableRedisHttpSession
@EnableCaching
public class ItbookFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItbookFrontApplication.class, args);
    }

}
