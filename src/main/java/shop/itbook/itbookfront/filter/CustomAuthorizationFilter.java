package shop.itbook.itbookfront.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * 로그인을 위한 Custom Filter 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
public class CustomAuthorizationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthorizationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        String memberId = request.getParameter("memberId");
        String password= request.getParameter("password");

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(memberId, password)
        );
    }

}
