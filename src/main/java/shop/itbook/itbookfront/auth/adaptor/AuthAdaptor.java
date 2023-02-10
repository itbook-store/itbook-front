package shop.itbook.itbookfront.auth.adaptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.dto.request.MemberOAuthRequestDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.auth.dto.request.MemberAuthRequestDto;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.auth.exception.LoginFailException;

/**
 * RestTemplate 통신 메서드를 제공해주는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthAdaptor {

    private final GatewayConfig gatewayConfig;

    private final RestTemplate restTemplate;

    private static final String AUTH_LOGIN_PROCESSING_URL = "/auth/login";

    private static final String SHOP_API_URL = "/api/members/oauth/login/find";

    private static final String REISSUE_REQUEST_URI = "/auth/token/reissue";

    private static final String AUTH_LOGOUT_PROCESSING_URL = "/auth/logout";

    private static final String AUTH_HEADER = "Authorization";

    private static final String HEADER_PREFIX = "Bearer ";


    /**
     * RestTemplate 을 통해 Auth 서버에 로그인을 요청하는 메서드 입니다.
     *
     * @return the adaptor
     * @author 강명관
     */
    public ResponseEntity<CommonResponseBody<Void>> postAuthServerForLogin(MemberAuthRequestDto memberAuthRequestDto) {

        ResponseEntity<CommonResponseBody<Void>> result = null;

        try {
            result = restTemplate.exchange(
                gatewayConfig.getGatewayServer() + AUTH_LOGIN_PROCESSING_URL,
                HttpMethod.POST,
                new HttpEntity<>(memberAuthRequestDto, new HttpHeaders()),
                new ParameterizedTypeReference<>() {
                });
        } catch (ResourceAccessException e) {
            throw new LoginFailException();
        }

        return result;
    }

    public ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> postShopServerCheckValidAuthUser(MemberOAuthRequestDto memberOAuthRequestDto) {

        return restTemplate.exchange(
            gatewayConfig.getGatewayServer() + SHOP_API_URL,
            HttpMethod.POST,
            new HttpEntity<>(memberOAuthRequestDto, new HttpHeaders()),
            new ParameterizedTypeReference<>() {
            }
        );
    }

    public ResponseEntity<CommonResponseBody<TokenDto>> postReissueToken(String refreshToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, HEADER_PREFIX + refreshToken);

        return restTemplate.exchange(
            gatewayConfig.getGatewayServer() + REISSUE_REQUEST_URI,
            HttpMethod.POST,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<>() {
            }
        );
    }

    public void requestAuthServerForLogout(Long memberNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Auth-MemberNo", String.valueOf(memberNo));

         restTemplate.exchange(
            gatewayConfig.getGatewayServer() + AUTH_LOGOUT_PROCESSING_URL,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            Void.class
        );
    }

}

