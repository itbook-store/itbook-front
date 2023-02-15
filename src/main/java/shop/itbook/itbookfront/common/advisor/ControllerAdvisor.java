package shop.itbook.itbookfront.common.advisor;

import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.MemberForbiddenException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.payment.exception.InvalidPaymentException;
import shop.itbook.itbookfront.product.exception.InvalidInputException;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
        BadRequestException.class,
        MethodArgumentNotValidException.class})
    public String badRequestException400(Exception e) {
        log.error("badRequestException400 {}", e);
        return "/error/400error";
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {MemberForbiddenException.class})
    public String forbiddenException403(Exception e) {
        log.error("forbiddenException403 {}", e);
        return "/error/403error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {
        RestApiServerException.class,
        RuntimeException.class,
        Exception.class
    })
    public String internalErrorException500(Exception e, Model model) {
        log.error("internalErrorException500 {}", e);
        model.addAttribute(MESSAGE, "ㅠㅠ 서버 오류입니다. 010-3338-1718, 010-5651-3199, 010-8601-9261, 010-5737-2315 개발자에게 직접 문의주세요 ^^");

        return "/error/500error";
    }

}
