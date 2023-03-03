package shop.itbook.itbookfront.cart.interceptor;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;
import static shop.itbook.itbookfront.cart.util.CartConstant.FIVE_MINUTE;
import static shop.itbook.itbookfront.cart.util.CartConstant.SUF_FIX;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.itbook.itbookfront.cart.annotation.Cart;

/**
 * 장바구니 이용시 필요한 쿠키를 없으면 생성하기 위한 인터셉터입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
public class CartInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Integer SIX_HOUR = 60 * 60 * 6;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        if (Objects.isNull(cookies) || cookies.length == 0) {
            createNewCookie(response);
            return true;
        }

        Cookie cartCookie = getCartCookieOrElseCreateCookie(cookies, response);
        cartCookie.setMaxAge(SIX_HOUR);

        /* 만약 CartController 에 접근한다면 Redis에 데이터가 존재할 꺼고, 그렇다면 해당 만료시간을 지정해준다.*/
        try {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation methodAnnotation = handlerMethod.getMethodAnnotation(Cart.class);
            if (Objects.nonNull(methodAnnotation)) {
                /* 만약 레디스에 데이터가 존재하지 않는다면 -2, 존재한다면 하루가 된다.*/
                /* 또한 계속 장바구니쪽을 이용하게 된다면 레디스 시간이 하루로 늘어나게 된다.*/
                redisTemplate.expire(cartCookie.getValue(), 6, TimeUnit.HOURS);

                Long expire = redisTemplate.getExpire(cartCookie.getValue());

                if (expire > 0) {
                    /* Phantom Key Set ExpireTime */
                    redisTemplate.expire(cartCookie.getValue() + SUF_FIX, expire - FIVE_MINUTE,
                        TimeUnit.SECONDS);
                }

            }
        } catch (Exception e) {

        }

        return true;
    }

    private static Cookie createNewCookie(HttpServletResponse response) {
        Cookie newCookie = new Cookie(COOKIE_NAME, "CID=" + UUID.randomUUID());
        newCookie.setMaxAge(SIX_HOUR);
        newCookie.setSecure(true);
        newCookie.setHttpOnly(true);
        newCookie.setPath("/");

        response.addCookie(newCookie);

        return newCookie;
    }

    private static Cookie getCartCookieOrElseCreateCookie(Cookie[] cookies,
                                                          HttpServletResponse response) {

        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
            .findFirst()
            .orElseGet(() -> createNewCookie(response));
    }
}
