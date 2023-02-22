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
 * 장바구니 이용시 필요한 쿠키를 없으면 생성하기 위한 인터셉터입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
public class CartInterceptor implements HandlerInterceptor {

    private static final Integer ONE_DAY = 60 * 60 * 24;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        if (Objects.isNull(cookies) || cookies.length == 0) {
            createNewCookie(response);
            return true;
        }

        getCartCookie(cookies, response);

        return true;
    }

    private static Cookie createNewCookie(HttpServletResponse response) {
        Cookie newCookie = new Cookie(COOKIE_NAME, "CID="+ UUID.randomUUID());
        newCookie.setMaxAge(ONE_DAY);
        newCookie.setSecure(true);
        newCookie.setHttpOnly(true);

        response.addCookie(newCookie);

        return newCookie;
    }

    private static Cookie getCartCookie(Cookie[] cookies, HttpServletResponse response) {

        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
            .findFirst()
            .orElseGet(() -> createNewCookie(response));
    }
}
