package shop.itbook.itbookfront.product.exception;

/**
 * @author 이하늬
 * @since 1.0
 */
public class RestApiServerException extends RuntimeException {
    public RestApiServerException(String resultMessage) {
        super(resultMessage);
    }
}
