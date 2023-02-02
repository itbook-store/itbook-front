package shop.itbook.itbookfront.cart.interceptor;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 강명관
 * @since 1.0
 */

@Slf4j
public class CartInterceptor implements HandlerInterceptor {

    private static final Integer ONE_DAY = 60 * 60 * 24;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        log.info("-------------- Cart Interceptor Start --------------");

        Cookie cartCookie = getCartCookie(request);

        if (Objects.isNull(cartCookie)) {
            Cookie newCookie = new Cookie(COOKIE_NAME, "CID="+ UUID.randomUUID());
            newCookie.setMaxAge(ONE_DAY);
            response.addCookie(newCookie);
        }
        return true;
    }

    private static Cookie getCartCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
            .findFirst()
            .orElse(null);
    }
}
