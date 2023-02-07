package shop.itbook.itbookfront.common.advisor;

import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.exception.MemberForbiddenException;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
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
    @ExceptionHandler(value = {BadRequestException.class, ValidationException.class})
    public String badRequestException400(Exception e) {
        log.error("badRequestException400 {}", e);
        return "/error/400error";
    }

//    @ExceptionHandler(value = {})

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
        model.addAttribute(MESSAGE, e.getMessage());

        return "/error/500error";
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
//    public String processValidationError(MethodArgumentNotValidException exception) {
//        BindingResult bindingResult = exception.getBindingResult();
//
//        StringBuilder builder = new StringBuilder();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            builder.append("[");
//            builder.append(fieldError.getField());
//            builder.append("](은)는 ");
//            builder.append(fieldError.getDefaultMessage());
//            builder.append(" 입력된 값: [");
//            builder.append(fieldError.getRejectedValue());
//            builder.append("]");
//        }
//
//        throw new InvalidInputException(builder.toString());
//
//    }

}
