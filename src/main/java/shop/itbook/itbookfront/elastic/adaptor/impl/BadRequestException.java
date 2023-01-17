package shop.itbook.itbookfront.elastic.adaptor.impl;

/**
 * @author 송다혜
 * @since 1.0
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String resultMessage) {
        super(resultMessage);
    }
}