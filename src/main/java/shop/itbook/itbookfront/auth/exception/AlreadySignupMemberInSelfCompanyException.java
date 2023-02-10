package shop.itbook.itbookfront.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 이미 자사 회원가입되어있는 회원이 소셜로 접근할 경우의 Exception
 *
 * @author 강명관
 * @since 1.0
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadySignupMemberInSelfCompanyException extends AuthenticationException {

    private static final String MESSAGE = "이미 자사 회원가입이 된 회원입니다.";

    public AlreadySignupMemberInSelfCompanyException() {
        super(MESSAGE);
    }
}
