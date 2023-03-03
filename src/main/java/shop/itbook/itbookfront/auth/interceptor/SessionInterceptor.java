package shop.itbook.itbookfront.auth.interceptor;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 사용자의 세션을 계속 늘려주는 인터셉터 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final String ITBOOK_SESSION = "ITBOOK";

    private static final Integer ONE_DAY = 60 * 60 * 24;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        if (Objects.isNull(cookies) || cookies.length == 0) {
            return true;
        }

        Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(ITBOOK_SESSION))
            .findFirst()
            .ifPresent(cookie -> cookie.setMaxAge(ONE_DAY));

        return true;
    }
}
