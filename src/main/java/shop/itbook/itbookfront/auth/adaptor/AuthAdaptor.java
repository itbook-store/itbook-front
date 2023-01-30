package shop.itbook.itbookfront.auth.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.auth.dto.request.MemberOAuthRequestDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.auth.dto.request.MemberAuthRequestDto;

/**
 * RestTemplate 통신 메서드를 제공해주는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class AuthAdaptor {

    private final RestTemplate restTemplate;

    /**
     * RestTemplate 을 통해 Auth 서버에 로그인을 요청하는 메서드 입니다.
     *
     * @param url the url
     * @return the adaptor
     * @author 강명관
     */
    public ResponseEntity<CommonResponseBody<Void>> postAuthServerForLogin(
        String url, MemberAuthRequestDto memberAuthRequestDto) {

        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(memberAuthRequestDto, new HttpHeaders()),
            new ParameterizedTypeReference<>() {
            });
    }

    public ResponseEntity<CommonResponseBody<Boolean>> postShopServerOAuthUserSignUp(String url, MemberOAuthRequestDto memberOAuthRequestDto) {

        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(memberOAuthRequestDto, new HttpHeaders()),
            new ParameterizedTypeReference<>() {
            }
        );
    }
}

