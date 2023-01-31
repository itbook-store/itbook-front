package shop.itbook.itbookfront.auth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 로그인을 위한 Custom Filter 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
public class CustomAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
        throws AuthenticationException {

        String memberId = request.getParameter("memberId");
        String password = request.getParameter("password");

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(memberId, password)
        );
    }

}
