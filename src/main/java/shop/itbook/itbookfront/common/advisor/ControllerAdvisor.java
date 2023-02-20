package shop.itbook.itbookfront.common.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.exception.JwtExpirationException;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.MemberForbiddenException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;

/**
 * 프론트 서버에서 에러를 처리하기 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";
    private static final String BAD_REQUEST = "badRequestMessage";
    private static final String BAD_REQUEST_MESSAGE = "잘못된 요청입니다.";
    private static final String INTERNAL_ERROR_MESSAGE =
        "ㅠㅠ 서버 오류입니다.\n010-3338-1718, 010-5651-3199, 010-8601-9261, 010-5737-2315, 010-8519-6955\n개발자에게 직접 문의주세요 ^___^";

    @ExceptionHandler(value = {
        BadRequestException.class,
        MethodArgumentNotValidException.class,
        BindException.class})
    public String badRequestException400(Exception e, RedirectAttributes redirectAttributes) {
        log.error("badRequestException400 {}", e.getMessage());
        redirectAttributes.addFlashAttribute(BAD_REQUEST, BAD_REQUEST_MESSAGE);
        return "redirect:/";
    }

    @ExceptionHandler(value = {JwtExpirationException.class})
    public String jwtException(Exception e) {
        log.error("jwtExpirationException {}", e.getMessage());
        return "redirect:/logout";
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {MemberForbiddenException.class})
    public String forbiddenException403(Exception e) {
        log.error("forbiddenException403 {}", e.getMessage());
        return "/error/403error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {
        RestApiServerException.class,
        RuntimeException.class,
        Exception.class
    })
    public String internalErrorException500(Exception e, Model model) {
        log.error("internalErrorException500 {}", e.getMessage());

        model.addAttribute(MESSAGE, INTERNAL_ERROR_MESSAGE);
        return "/error/500error";
    }

}
