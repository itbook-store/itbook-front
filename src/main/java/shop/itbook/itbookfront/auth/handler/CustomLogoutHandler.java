package shop.itbook.itbookfront.auth.handler;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import shop.itbook.itbookfront.common.exception.BadRequestException;

/**
 * 로그아웃을 담당하는 Custom Handler 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private static final String MESSAGE = "로그인되지 않은 유저입니다.";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new BadRequestException(MESSAGE);
        }

        session.removeAttribute((String) authentication.getPrincipal());
        session.invalidate();


        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
    }
}
