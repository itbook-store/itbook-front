package shop.itbook.itbookfront.order.exception;

import shop.itbook.itbookfront.common.exception.BadRequestException;

/**
 * @author 정재원
 * @since 1.0
 */
public class InvalidOrderException extends BadRequestException {

    private static final String MESSAGE = "유효하지 않은 접근입니다.";

    public InvalidOrderException() {
        super(MESSAGE);
    }
}
