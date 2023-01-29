package shop.itbook.itbookfront.auth.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.auth.exception.MemberNotFountException;

/**
 * RestTemplate 요청 전에 Request Header에 Authorization을 추가해주는 인터셉터 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
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

        TokenDto tokenDto = (TokenDto) session.getAttribute("tokenDto");
        String accessToken = tokenDto.getAccessToken();

        log.info("tokenDto {}", tokenDto);

//        TokenDto tokenDto = getTokenDto(session);
//        String accessToken = tokenDto.getAccessToken();

        request.getHeaders().add(AUTH_HEADER, HEADER_PREFIX + accessToken);

        return execution.execute(request, body);
    }

//    private TokenDto getTokenDto(HttpSession session) throws JsonProcessingException {
//        String tokenDtoJsonString = String.valueOf(session.getAttribute("tokenDto"));
//
//        if (Objects.isNull(tokenDtoJsonString)) {
//            throw new MemberNotFountException();
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(tokenDtoJsonString, TokenDto.class);
//    }

}
