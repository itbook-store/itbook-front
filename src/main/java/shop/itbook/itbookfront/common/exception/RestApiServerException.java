package shop.itbook.itbookfront.common.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class RestApiServerException extends RuntimeException {
    public RestApiServerException(String resultMessage) {
        super(resultMessage);
    }
}
