package shop.itbook.itbookfront.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.auth.dto.request.MemberAuthRequestDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.exception.LoginFailException;

/**
 * Auth 서버와 통신하는 공통 로직을 정의한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final AuthAdaptor authAdaptor;
    private final GatewayConfig gatewayConfig;

    private static final String AUTH_LOGIN_PROCESSING_URL = "/auth/login";
    private static final String HEADER_USER_DETAIL = "Authorities-UserDetails";
    private static final String HEADER_TOKEN = "Authorities-Token";
    private static final String HEADER_AUTHORITIES = "Authorities";

    private UserDetailsDto userDetailsDto;
    private TokenDto tokenDto;
    private List<SimpleGrantedAuthority> authorities;

    /**
     * Auth 서버와 통신하여 UserDetailsDto, TokenDto, Authorities 를 받아오는 공통 로직입니다.
     *
     * @param memberId 클라이언트가 로그인 입력한 ID 값
     * @param rawPassword 클라이언트가 로그인 입력한 Password 값
     * @author 강명관
     */
    public void requestAuthorization(String memberId, String rawPassword) {
        ResponseEntity<CommonResponseBody<Void>> exchange =
            authAdaptor.postAuthServerForLogin(
                gatewayConfig.getGatewayServer() + AUTH_LOGIN_PROCESSING_URL,
                new MemberAuthRequestDto(
                    memberId,
                    rawPassword
                )
            );

        HttpHeaders headers = exchange.getHeaders();
        String authUserDetails = getAuthUserDetails(headers);
        String authToken = getAuthToken(headers);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.userDetailsDto = objectMapper.readValue(authUserDetails, UserDetailsDto.class);
            this.tokenDto = objectMapper.readValue(authToken, TokenDto.class);
            this.authorities = getGrantedAuthoritiesToResponseHeader(headers);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Auth서버에서 잘못된 요청이 넘어왔습니다.");
        }

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
     * Auth 서버로부터 넘어온 TokenDto를 얻어오는 메서드입니다.
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
     * Auth 서버로부터 넘어온 AccessToken을 얻어오는 메서드입니다.
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

    public UserDetailsDto getUserDetailsDto() {
        return this.userDetailsDto;
    }

    public TokenDto getTokenDto() {
        return this.tokenDto;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

}
