package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class UnableToCreateCouponException extends RuntimeException {
    public static final String MESSAGE = "쿠폰 수량이 소진되어 발급이 불가합니다.";

    public UnableToCreateCouponException() {
        super(MESSAGE);
    }
}
