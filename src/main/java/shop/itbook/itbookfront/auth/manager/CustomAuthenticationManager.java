package shop.itbook.itbookfront.auth.manager;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.auth.util.AuthUtil;

/**
 * Auth 서버와 통신하여 로그인에 대한 처리에 대한 Response 를 담당하는 Custom AuthenticationManager 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final AuthUtil authUtil;

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

        authUtil.requestAuthorization(
            (String) authentication.getPrincipal(),
            (String) authentication.getCredentials()
        );

        UserDetailsDto userDetailsDto = authUtil.getUserDetailsDto();
        TokenDto tokenDto = authUtil.getTokenDto();
        List<SimpleGrantedAuthority> authorities = authUtil.getAuthorities();

        HttpServletRequest request =
            Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession(true);
        session.setAttribute("tokenDto", tokenDto);

        return new UsernamePasswordAuthenticationToken(
            userDetailsDto,
            null,
            authorities
        );
    }
}
