package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class InvalidPathRequestCouponList extends Exception{
    public static final String MESSAGE = "잘못된 쿠폰 목록 요청입니다.";

    public InvalidPathRequestCouponList() {
        super(MESSAGE);
    }
}
