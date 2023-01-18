package shop.itbook.itbookfront.auth.manager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import shop.itbook.itbookfront.adaptor.RestTemplateAdaptor;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.exception.LoginFailException;
import shop.itbook.itbookfront.login.dto.MemberAuthRequestDto;

/**
 * Auth 서버와 통신하여 로그인에 대한 처리에 대한 Response 를 담당하는 Custom AuthenticationManager 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final RestTemplateAdaptor restTemplateAdaptor;
    private final GatewayConfig gatewayConfig;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String AUTH_LOGIN_PROCESSING_URL = "/auth/login";

    /**
     * AuthenticationManager 의 custom 구현 메서드로, Auth서버로 부터 RestTemplate로 인증을 요청하고,
     * JWT AccessToken을 받아 Front Server 의 Security Context로부터 관리하기 위한 메서드 입니다.
     *
     * @param authentication the authentication request object
     * @return 인증이 완료된 AuthenticationToken
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplateAdaptor.postAuthServerForLogin(
                gatewayConfig.getGatewayServer() + AUTH_LOGIN_PROCESSING_URL,
                new MemberAuthRequestDto((String) authentication.getPrincipal(),
                    (String) authentication.getCredentials())
            );

//        ResponseChecker.checkFail(
//            exchange.getStatusCode(),
//            exchange.getBody().getHeader()
//        );

        String accessToken = Optional.ofNullable(exchange.getHeaders().get("Authorization"))
            .orElseThrow(LoginFailException::new)
            .get(0);

        List<SimpleGrantedAuthority> grantedAuthorities =
            Optional.ofNullable(exchange.getHeaders().get("Authorities"))
                .orElseThrow(LoginFailException::new)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        redisTemplate.opsForHash().put("accessToken", authentication.getPrincipal(), accessToken);

        return new UsernamePasswordAuthenticationToken(
            authentication.getPrincipal(),
            null,
            grantedAuthorities
        );
    }
}
