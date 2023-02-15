package shop.itbook.itbookfront.auth.exception;

/**
 * @author 강명관
 * @since 1.0
 */
public class JwtExpirationException extends RuntimeException {

    private static final String MESSAGE = "로그인이 만료되었습니다.";

    public JwtExpirationException() {
        super(MESSAGE);
    }
}
