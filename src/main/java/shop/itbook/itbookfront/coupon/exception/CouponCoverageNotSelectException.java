package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponCoverageNotSelectException extends RuntimeException {
    public static final String MESSAGE = "쿠폰 적용범위가 선택되지 않았습니다.";

    public CouponCoverageNotSelectException() {
        super(MESSAGE);
    }
}
