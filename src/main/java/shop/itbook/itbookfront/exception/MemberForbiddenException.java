package shop.itbook.itbookfront.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class MemberForbiddenException extends RuntimeException {

    public static final String MESSAGE = "멤버 권한이 없습니다. ";

    public MemberForbiddenException(String resultMessage) {
        super(MESSAGE);
    }
}
