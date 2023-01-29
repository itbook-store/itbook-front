package shop.itbook.itbookfront.auth.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.exception.LoginFailException;
import shop.itbook.itbookfront.auth.dto.request.MemberAuthRequestDto;
import shop.itbook.itbookfront.util.ResponseChecker;

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
    private static final String HEADER_USER_DETAIL = "Authorities-UserDetails";
    private static final String HEADER_TOKEN = "Authorities-Token";
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
                new MemberAuthRequestDto(
                    (String) authentication.getPrincipal(),
                    (String) authentication.getCredentials()
                )
            );

//        ResponseChecker.checkFail(
//            exchange.getStatusCode(),
//            exchange.getBody().getHeader().getResultMessage()
//        );

        HttpHeaders headers = exchange.getHeaders();
        String authUserDetails = getAuthUserDetails(headers);
        String authToken = getAuthToken(headers);

        ObjectMapper objectMapper = new ObjectMapper();
        UserDetailsDto userDetailsDto;
        TokenDto tokenDto;

        try {
            userDetailsDto = objectMapper.readValue(authUserDetails, UserDetailsDto.class);
            tokenDto = objectMapper.readValue(authToken, TokenDto.class);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Auth서버에서 잘못된 요청이 넘어왔습니다.");
        }



        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession(true);
        session.setAttribute("tokenDto", tokenDto);

        return new UsernamePasswordAuthenticationToken(
            userDetailsDto,
            null,
            getGrantedAuthoritiesToResponseHeader(headers)
        );
    }

    /**
     * RestTemplate로 부터 넘어온 UserDetailsDto를 얻어오는 메서드입니다.
     *
     * @param headers RestTemplate로 부터 넘어온 Response 입니다.
     * @return Auth서버로 부터 넘어온 UserDetailsDto 입니다.
     */
    private String getAuthUserDetails(HttpHeaders headers) {
        return Optional.ofNullable(headers.get(HEADER_USER_DETAIL))
            .orElseThrow(LoginFailException::new)
            .get(0);
    }

    /**
     * RestTemplate로 부터 넘어온 TokenDto를 얻어오는 메서드입니다.
     *
     * @param headers RestTemplate로 부터 넘어온 Response 입니다.
     * @return Auth서버로 부터 넘어온 TokenDto 입니다.
     */
    private String getAuthToken(HttpHeaders headers) {
        return Optional.ofNullable(headers.get(HEADER_TOKEN))
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

}
