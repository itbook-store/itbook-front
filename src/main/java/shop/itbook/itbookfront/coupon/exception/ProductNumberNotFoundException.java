package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class ProductNumberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "상품 쿠폰의 상품 번호가 없습니다.";

    public ProductNumberNotFoundException() {
        super(MESSAGE);
    }
}