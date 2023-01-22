package shop.itbook.itbookfront.auth.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StreamUtils;

/**
 * @author 강명관
 * @since 1.0
 */

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SUCCESS_URL = "/";

    private final RedisTemplate<String, String> redisTemplate;

    public CustomLoginSuccessHandler(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        log.info("session.getId() {}", session.getId());

//        String session1 = response.getHeader("Session");
//        log.info("response Session {}", session1);

        log.info("uuid {}", authentication.getPrincipal());
        log.info("token details {}", authentication.getDetails());
        session.setAttribute((String) authentication.getPrincipal(), authentication.getDetails());


        //        response.sendRedirect(SUCCESS_URL);

        getRedirectStrategy().sendRedirect(request, response, SUCCESS_URL);


    }
}
