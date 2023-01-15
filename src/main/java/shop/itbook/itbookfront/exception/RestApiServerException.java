package shop.itbook.itbookfront.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class RestApiServerException extends RuntimeException{
    public RestApiServerException(String resultMessage) {
        super(resultMessage);
    }
}
