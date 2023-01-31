package shop.itbook.itbookfront.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 지원하지 않는 OAuth Server 에 대한 Exception
 *
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOAuthServerException extends RuntimeException {

    private static final String MESSAGE = "지원되지 않는 OAuth 서버 입니다.";

    public InvalidOAuthServerException() {
        super(MESSAGE);
    }
}
