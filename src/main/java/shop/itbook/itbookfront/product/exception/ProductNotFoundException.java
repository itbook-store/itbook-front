package shop.itbook.itbookfront.product.exception;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductNotFoundException extends RuntimeException {
    public static final String MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
