package shop.itbook.itbookfront.product.adaptor;

/**
 * @author 최겸준
 * @since 1.0
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String resultMessage) {
        super(resultMessage);
    }
}
