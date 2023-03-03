package shop.itbook.itbookfront.auth.interceptor;

import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.itbook.itbookfront.auth.adaptor.AuthAdaptor;
import shop.itbook.itbookfront.auth.dto.TokenDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;

/**
 * 사용자의 JWT 토큰을 재발급 요청하는 인터셉터 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenReissueInterceptor implements HandlerInterceptor {

    private final AuthAdaptor authAdaptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            return true;
        }

        TokenDto tokenDto = (TokenDto) session.getAttribute("tokenDto");

        if (Objects.isNull(tokenDto)) {
            return true;
        }

        Date refreshTokenExpirationTime = tokenDto.getRefreshTokenExpirationTime();
        Date accessTokenExpirationTime = tokenDto.getAccessTokenExpirationTime();

        Date now = new Date();


//        if (!refreshTokenExpirationTime.after(now)) {
//            response.sendRedirect("/logout");
//            return true;
//        }

        if (refreshTokenExpirationTime.after(now) && accessTokenExpirationTime.before(now)) {
            ResponseEntity<CommonResponseBody<TokenDto>> result = authAdaptor.postReissueToken(
                tokenDto.getRefreshToken()
            );

            TokenDto reissueTokenDto = Objects.requireNonNull(result.getBody()).getResult();

            session.setAttribute("tokenDto", reissueTokenDto);
        }

        return true;
    }
}
