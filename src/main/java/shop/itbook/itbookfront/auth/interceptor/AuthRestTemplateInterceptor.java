package shop.itbook.itbookfront.auth.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;

/**
 * RestTemplate 요청 전에 Request Header에 Authorization을 추가해주는 인터셉터 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
public class AuthRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final String AUTH_TOKEN_HEADER = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";

    private final List<String> loginPath = List.of("/login", "/auth/login", "/api/members/oauth/login/find");

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        if (loginPath.contains(request.getURI().getPath())) {
            return execution.execute(request, body);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetailsDto)) {
            return execution.execute(request, body);
        }

        HttpServletRequest httpServletRequest =
            Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = httpServletRequest.getSession(false);

        TokenDto tokenDto = (TokenDto) session.getAttribute("tokenDto");
        String accessToken = tokenDto.getAccessToken();

        request.getHeaders().add(AUTH_TOKEN_HEADER, HEADER_PREFIX + accessToken);

        return execution.execute(request, body);
    }
}
