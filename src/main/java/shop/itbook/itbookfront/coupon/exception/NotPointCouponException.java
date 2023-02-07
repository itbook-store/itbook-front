package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class NotPointCouponException extends RuntimeException {
    public static final String MESSAGE = "포인트 쿠폰이 아닙니다.";

    public NotPointCouponException() {
        super(MESSAGE);
    }
}
