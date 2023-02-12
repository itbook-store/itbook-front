package shop.itbook.itbookfront.auth.handler;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.auth.util.AuthUtil;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * OAuth2 로그인이 성공적으로 된 후, OAuth2 Server 에서 받은 정보를 통해 Auth서버로 보내 인가를 요청하기위한
 * CustomSuccessHandler 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthUtil authUtil;

    private final CartService cartService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static final String DEFAULT_SUCCESS_URL = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        authUtil.requestAuthorization(email, email);

        UserDetailsDto userDetailsDto = authUtil.getUserDetailsDto();
        TokenDto tokenDto = authUtil.getTokenDto();
        List<SimpleGrantedAuthority> authorities = authUtil.getAuthorities();

        HttpSession session = request.getSession(true);
        session.setAttribute("tokenDto", tokenDto);

        Authentication oAuthAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetailsDto,
            null,
            authorities
        );

        Cookie[] cookies = request.getCookies();

        Cookie cartCookie = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
            .findFirst()
            .orElse(
                new Cookie(COOKIE_NAME, "CID=" + UUID.randomUUID())
            );

        cartService.loadCartProductForMember(cartCookie.getValue(), userDetailsDto.getMemberNo());

        SecurityContextHolder.getContext().setAuthentication(oAuthAuthenticationToken);

        redirectStrategy.sendRedirect(request, response, DEFAULT_SUCCESS_URL);
    }
}
