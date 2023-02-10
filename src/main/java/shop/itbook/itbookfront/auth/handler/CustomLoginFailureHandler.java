package shop.itbook.itbookfront.auth.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import shop.itbook.itbookfront.auth.exception.AlreadySignupMemberInSelfCompanyException;

/**
 * 로그인 실패 처리시, 다시 로그인 페이지로 리다이렉트 하며, 메세지를 뛰워주기 위한 핸들러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private static final String BASIC_FAIL_MESSAGE = "아이디 혹은 비밀번호가 틀렸습니다.";

    private static final String OAUTH_FAIL_MESSAGE = "이미 자사 회원 가입이 된 회원 입니다.";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
        throws IOException, ServletException {

        log.error("Exception get Message {}", exception.getMessage());

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        if (exception instanceof AlreadySignupMemberInSelfCompanyException) {
            redirectStrategy.sendRedirect(request, response,
                "/login?error=" + URLEncoder.encode(OAUTH_FAIL_MESSAGE, StandardCharsets.UTF_8));
        }

        redirectStrategy.sendRedirect(request, response,
            "/login?error=" + URLEncoder.encode(BASIC_FAIL_MESSAGE, StandardCharsets.UTF_8));
    }
}
