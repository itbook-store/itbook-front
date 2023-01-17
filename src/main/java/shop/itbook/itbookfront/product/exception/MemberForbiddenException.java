package shop.itbook.itbookfront.product.exception;

/**
 * @author 이하늬
 * @since 1.0
 */
public class MemberForbiddenException extends RuntimeException {

    public static final String MESSAGE = "죄송합니다 고객님 ";

    public MemberForbiddenException(String resultMessage) {
        super(MESSAGE);
    }
}
