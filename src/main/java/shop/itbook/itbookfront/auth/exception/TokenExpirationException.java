package shop.itbook.itbookfront.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenExpirationException extends RuntimeException {

    private static final String MESSAGE = "로그인이 만료 되었습니다.";

    public TokenExpirationException() {
        super(MESSAGE);
    }
}
