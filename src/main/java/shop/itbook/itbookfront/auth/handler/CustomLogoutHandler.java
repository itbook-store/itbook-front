package shop.itbook.itbookfront.auth.handler;

import java.io.IOException;
import java.util.Objects;
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
        authAdaptor.requestAuthServerForLogout(principal.getMemberNo());

        session.removeAttribute("ITBOOK_SESSIONID");
        session.invalidate();

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

    }
}
