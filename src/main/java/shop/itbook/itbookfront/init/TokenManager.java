package shop.itbook.itbookfront.init;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;

/**
 * @author 이하늬
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class TokenManager /*implements InitializingBean*/ {

    private final RestTemplate restTemplate;

    @Value("${object.storage.ternant-id}")
    private String ternantId;

    @Value("${object.storage.username}")
    private String username;

    @Value("${object.storage.password}")
    private String password;

    /*@Override*/
    public void afterPropertiesSet() throws Exception {
        String url = "https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens";
        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(ternantId, new PasswordCredentials(username, password));
        restTemplate.postForObject(url, tokenRequestDto, TokenResponseDto.class);
    }
}
