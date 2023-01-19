package shop.itbook.itbookfront.exception;

/**
 * 로그인에 실패 하였을 경우의 Exception
 *
 * @author 강명관
 * @since 1.0
 */
public class LoginFailException extends RuntimeException{

    private static final String MESSAGE = "로그인에 실패하였습니다.";

    public LoginFailException() {
        super(MESSAGE);
    }
}
