package shop.itbook.itbookfront.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
//            .setReadTimeout(Duration.ofSeconds(30L))
//            .setConnectTimeout(Duration.ofSeconds(30L))
            .build();
    }
}
