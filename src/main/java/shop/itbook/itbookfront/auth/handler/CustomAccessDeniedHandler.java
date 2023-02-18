package shop.itbook.itbookfront.auth.handler;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Security 에서 발생하는 AccessDeniedException 을 잡아 처리하기 위한 핸들러 클래스입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
        throws IOException {

        log.error("AccessDenied error {}", accessDeniedException.getMessage());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            log.error("Request URI {}", request.getRequestURI());
            log.error("Authentication Token Info {}", authentication);
        }


        response.sendRedirect("/error/403error");
    }
}
