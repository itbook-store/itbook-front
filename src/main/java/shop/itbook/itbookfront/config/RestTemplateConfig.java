package shop.itbook.itbookfront.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import shop.itbook.itbookfront.common.handler.RestTemplateResponseErrorHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder
//            .setReadTimeout(Duration.ofSeconds(30L))
//            .setConnectTimeout(Duration.ofSeconds(30L))
            .build();

        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler(new ObjectMapper()));
        return restTemplate;
    }
}
