package shop.itbook.itbookfront.cart.util;

/**
 * 장바구니에서 자주 사용되는 상수 클래스입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class CartConstant {

    private CartConstant() {

    }

    public static final String COOKIE_NAME = "ITBOOK_CART";
    public static final String PRE_FIX = "CID=";
    public static final String SUF_FIX = ":phantom";
    public static final Long FIVE_MINUTE = 300L;
    public static final String MEMBER_NO = "memberNo";
}
