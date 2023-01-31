package shop.itbook.itbookfront.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 회원의 정보가 존재하지 않을 경우에 대한 Exception 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFountException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 회원 입니다.";

    public MemberNotFountException() {
        super(MESSAGE);
    }
}
