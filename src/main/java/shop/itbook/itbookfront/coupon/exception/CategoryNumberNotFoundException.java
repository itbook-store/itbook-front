package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CategoryNumberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "카테고리 쿠폰의 카테고리 번호가 없습니다.";

    public CategoryNumberNotFoundException() {
        super(MESSAGE);
    }
}