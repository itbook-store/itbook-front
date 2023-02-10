package shop.itbook.itbookfront.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 로그인에 실패 하였을 경우의 Exception
 *
 * @author 강명관
 * @since 1.0
 */
public class LoginFailException extends AuthenticationException {

    private static final String MESSAGE = "로그인에 실패하였습니다.";

    public LoginFailException() {
        super(MESSAGE);
    }
}
