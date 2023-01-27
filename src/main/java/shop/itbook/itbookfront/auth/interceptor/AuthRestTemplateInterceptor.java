package shop.itbook.itbookfront.auth.interceptor;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * RestTemplate 요청 전에 Request Header에 Authorization을 추가해주는 인터셉터 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class AuthRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final String AUTH_HEADER = "Authorization";

    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        HttpServletRequest httpServletRequest =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = httpServletRequest.getSession(false);

        if (Objects.isNull(session)) {
            return execution.execute(request, body);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (Objects.isNull(principal)) {
            return execution.execute(request, body);
        }

        String accessToken = (String) session.getAttribute(String.valueOf(principal));

        request.getHeaders().add(AUTH_HEADER, HEADER_PREFIX + accessToken);

        return execution.execute(request, body);
    }
}
