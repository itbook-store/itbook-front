package shop.itbook.itbookfront.auth.handler;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.cart.service.CartService;
import shop.itbook.itbookfront.common.exception.BadRequestException;

/**
 * 로그아웃을 담당하는 Custom Handler 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final AuthAdaptor authAdaptor;

    private final CartService cartService;

    private static final String MESSAGE = "로그인되지 않은 유저입니다.";

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        HttpSession session = request.getSession(false);

        if (Objects.isNull(session) || Objects.isNull(authentication)) {
            response.addHeader("message", MESSAGE);
            try {
                redirectStrategy.sendRedirect(request, response, "/login");
                return;
            } catch (IOException e) {
                throw new BadRequestException(MESSAGE);
            }
        }

        UserDetailsDto principal = (UserDetailsDto) authentication.getPrincipal();

        try {
            authAdaptor.requestAuthServerForLogout(principal.getMemberNo());
        } catch (Exception e) {
            log.error("Auth Server Logout Filter Error {}", e.getMessage());
            e.printStackTrace();
        }

        try {
            Cookie[] cookies = request.getCookies();
            Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .ifPresent(cookie -> cartService.saveAllCartDataByRedis(cookie.getValue()));
        } catch (Exception e) {
            log.error("Logout Cart Save Error {}", e.getMessage());
            e.printStackTrace();
        }


        session.removeAttribute("tokenDto");
        session.invalidate();

        SecurityContext context = SecurityContextHolder.getContext();
        context.getAuthentication().setAuthenticated(false);
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

    }
}
