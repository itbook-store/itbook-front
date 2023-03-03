package shop.itbook.itbookfront.auth.handler;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CartService cartService;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String DEFAULT_SUCCESS_URL = "/";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();

        Cookie cartCookie = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
            .findFirst()
            .orElse(
                new Cookie(COOKIE_NAME, "CID=" + UUID.randomUUID())
            );

        UserDetailsDto principal = (UserDetailsDto) authentication.getPrincipal();

        cartService.loadCartProductForMember(cartCookie.getValue(), principal.getMemberNo());

        redirectStrategy.sendRedirect(request, response, DEFAULT_SUCCESS_URL);
    }
}
