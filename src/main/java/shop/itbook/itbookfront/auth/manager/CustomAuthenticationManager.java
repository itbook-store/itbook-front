package shop.itbook.itbookfront.auth.manager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.exception.LoginFailException;
import shop.itbook.itbookfront.auth.dto.request.MemberAuthRequestDto;

/**
 * Auth 서버와 통신하여 로그인에 대한 처리에 대한 Response 를 담당하는 Custom AuthenticationManager 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final AuthAdaptor authAdaptor;
    private final GatewayConfig gatewayConfig;

    private static final String AUTH_LOGIN_PROCESSING_URL = "/auth/login";

    private static final String HEADER_AUTHORIZATION = "Authorization";

    private static final String HEADER_UUID = "UUID";

    private static final String HEADER_AUTHORITIES = "Authorities";

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
            authAdaptor.postAuthServerForLogin(
                gatewayConfig.getGatewayServer() + AUTH_LOGIN_PROCESSING_URL,
                new MemberAuthRequestDto((String) authentication.getPrincipal(),
                    (String) authentication.getCredentials())
            );

//        ResponseChecker.checkFail(
//            exchange.getStatusCode(),
//            exchange.getBody().getHeader()
//        );

        HttpHeaders headers = exchange.getHeaders();

        String accessToken = getAccessTokenToResponseHeader(headers);
        List<SimpleGrantedAuthority> grantedAuthorities = getGrantedAuthoritiesToResponseHeader(headers);
        String uuid = getUuidToResponseHeader(headers);

        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession(true);
        session.setAttribute(uuid, accessToken);

        return new UsernamePasswordAuthenticationToken(
            uuid,
            null,
            grantedAuthorities
        );
    }

    /**
     * RestTemplate로 부터 넘어온 UUID를 얻어오는 메서드입니다.
     *
     * @param headers RestTemplate로 부터 넘어온 Response 입니다.
     * @return Authentication 객체의 Principal의 UUID 입니다.
     */
    private String getUuidToResponseHeader(HttpHeaders headers) {
            return Optional.ofNullable(headers.get(HEADER_UUID))
            .orElseThrow(LoginFailException::new)
            .get(0);
    }

    /**
     * RestTemplate로 부터 넘어온 AccessToken을 얻어오는 메서드입니다.
     *
     * @param headers RestTemplate로 부터 넘어온 Response 입니다.
     * @return Authentication 객체의 Authorities 입니다
     */
    private List<SimpleGrantedAuthority> getGrantedAuthoritiesToResponseHeader(HttpHeaders headers) {
            return Optional.ofNullable(headers.get(HEADER_AUTHORITIES))
                .orElseThrow(LoginFailException::new)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * RestTemplate로 부터 넘어온 AccessToken을 얻어오는 메서드입니다.
     *
     * @param headers RestTemplate로 부터 넘어온 Response 입니다.
     * @return Auth서버로 부터 넘어온 AccessToken 입니다.
     */
    private String getAccessTokenToResponseHeader(HttpHeaders headers) {
        return Optional.ofNullable(headers.get(HEADER_AUTHORIZATION))
            .orElseThrow(LoginFailException::new)
            .get(0);
    }
}
