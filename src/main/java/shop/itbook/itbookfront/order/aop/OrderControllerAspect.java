package shop.itbook.itbookfront.order.aop;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;

/**
 * @author 정재원
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
public class OrderControllerAspect {
    @Before(value = "execution(* shop.itbook.itbookfront.ordersheet.controller.serviceapi.OrderSheetController.*(..)) && args(nonMember, userDetailsDto, model, response)")
    public void beforeAdvice(JoinPoint joinPoint,
                             HttpServletResponse response,
                             @RequestParam(value = "nonMember", required = false) Boolean nonMember,
                             @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                             Model model) throws IOException {

        log.info("제발제발: {}", joinPoint.getSourceLocation());

        if (Objects.nonNull(userDetailsDto)) {
            model.addAttribute("orderSheetUrl", joinPoint.getSourceLocation());
            response.sendRedirect("/login");
            // 주문서 띄워줄려할때 로그인 검사를 한다. 로그인 되어 있으면 끝이고 안되어 있으면 로그인 페이지로 보낸다.
        }

    }
}
