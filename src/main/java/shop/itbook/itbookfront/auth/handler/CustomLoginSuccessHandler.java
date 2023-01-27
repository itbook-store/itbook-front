package shop.itbook.itbookfront.auth.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author 강명관
 * @since 1.0
 */

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SUCCESS_URL = "/";

    private final RedisTemplate redisTemplate;

    public CustomLoginSuccessHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        log.info("session.getId() {}", session.getId());

        String accessToken = response.getHeader("Authorization");
        String role = response.getHeader("role");
        log.info("accessToken {}", accessToken);
        log.info("==========================================");
        log.info("role {}", role);
        log.info("==========================================");


        String principal = (String) authentication.getPrincipal();
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        response.sendRedirect(SUCCESS_URL);

        super.onAuthenticationSuccess(request,response,authentication);

    }
}
