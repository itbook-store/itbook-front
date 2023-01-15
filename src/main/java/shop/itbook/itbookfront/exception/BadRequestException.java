package shop.itbook.itbookfront.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String resultMessage) {
        super(resultMessage);
    }
}
