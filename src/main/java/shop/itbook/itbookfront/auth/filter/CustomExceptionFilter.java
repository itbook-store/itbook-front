package shop.itbook.itbookfront.auth.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.itbook.itbookfront.auth.exception.AlreadySingupMemberInSelfCompanyException;
import shop.itbook.itbookfront.auth.exception.InvalidOAuthServerException;
import shop.itbook.itbookfront.exception.LoginFailException;

/**
 * 필터에서 발생하는 에러를 잡아 처리하기 위한 필터입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class CustomExceptionFilter extends OncePerRequestFilter {

    private static final String LOGIN_PATH = "/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (LoginFailException e) {
            log.error("loginFailException {}", e.getMessage());
            response.sendRedirect(LOGIN_PATH);
        } catch (AlreadySingupMemberInSelfCompanyException e) {
            log.error("AlreadySingupMemberInSelfCompanyException {}", e.getMessage());
            response.sendRedirect(LOGIN_PATH);
        } catch (InvalidOAuthServerException e) {
            log.error("InvalidOAuthServerException {}", e.getMessage());
            response.sendRedirect(LOGIN_PATH);
        }
    }
}
