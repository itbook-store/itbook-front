package shop.itbook.itbookfront.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFountException extends RuntimeException {

    private static final String MESSAGE = "로그인되어 있지 않은 회원입니다.";

    public MemberNotFountException() {
        super(MESSAGE);
    }
}
