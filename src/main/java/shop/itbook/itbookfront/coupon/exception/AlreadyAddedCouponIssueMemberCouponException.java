package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class AlreadyAddedCouponIssueMemberCouponException extends RuntimeException {
    public static final String MESSAGE = "이미 다운로드 받은 쿠폰입니다.";

    public AlreadyAddedCouponIssueMemberCouponException() {
        super(MESSAGE);
    }
}
