package shop.itbook.itbookfront.common.advisor;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class})
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoHandlerFoundException.class, NotFoundException.class})
    public String notFoundException404(Exception e) {
        log.error("notFoundException404 {}", e);
        return "/error/404error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {
        RestApiServerException.class,
        RuntimeException.class,
        Exception.class
    })
    public String internalErrorException500(Exception e, Model model) {
        log.error("internalErrorException500 {}", e);
        model.addAttribute(MESSAGE, e.getMessage());

        return "/error/500error";
    }

}
