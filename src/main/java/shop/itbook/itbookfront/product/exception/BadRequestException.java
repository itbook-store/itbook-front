package shop.itbook.itbookfront.product.exception;

/**
 * @author 이하늬
 * @since 1.0
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String resultMessage) {
        super(resultMessage);
    }
}
