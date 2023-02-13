package shop.itbook.itbookfront.coupon.exception;

/**
 * @author 송다혜
 * @since 1.0
 */
public class MembershipGradeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "멤버등급이 없습니다.";

    public MembershipGradeNotFoundException() {
        super(MESSAGE);
    }
}