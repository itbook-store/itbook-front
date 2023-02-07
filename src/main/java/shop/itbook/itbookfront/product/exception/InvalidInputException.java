package shop.itbook.itbookfront.product.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class InvalidInputException extends RuntimeException {

    public static final String MESSAGE = "입력된 값";

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException() {
        super(MESSAGE);


    }
}
