package shop.itbook.itbookfront.elastic.adaptor.impl;

/**
 * @author 송다혜
 * @since 1.0
 */
public class RestApiServerException extends RuntimeException{
    public RestApiServerException(String resultMessage) {
        super(resultMessage);
    }
}