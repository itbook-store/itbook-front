package shop.itbook.itbookfront.cart.service;

import java.util.List;

/**
 * 장바구니 비지니스로직의 서비스 인터페이스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

public interface CartService {

    /**
     * redis에 저장되어있는 장바구니 리스트를 가져오는 메서드 입니다.
     *
     * @param cookieValue 쿠기의 Value값을 통해 redis에 저장되어 있습니다.
     * @return 상품 디테일과, 상품 카운트가 저장되어 있는 CartProductDetailsResponseDto 입니다.
     * @author 강명관
     */
    List<Object> getCartList(String cookieValue);

    /**
     * redis에 상품을 저장하는 메서드 입니다.
     *
     * @param cookieValue 쿠기의 Value값을 통해 redis에 저장되어 있습니다.
     * @param productNo 상품번호
     * @return 성공 true, 실패 false
     * @author 강명관
     */
    boolean addCartProduct(String cookieValue, Integer productNo);

    /**
     * redis에 상품을 삭제하는 메서드 입니다.
     *
     * @param cookieValue 쿠기의 Value값을 통해 redis에 저장되어 있습니다.
     * @param productNo 상품번호
     * @author 강명관
     */
    void deleteCartProduct(String cookieValue, Integer productNo);

    /**
     * 상품 번호 리스트로 해당 상품들을 삭제하는 메서드 입니다.
     *
     * @param cookieValue 쿠기의 Value값을 통해 redis에 저장되어 있습니다.
     * @param productNoList 상품번호 리스트
     * @author 강명관
     */
    void deleteAllCartProduct(String cookieValue, List<Integer> productNoList);

    /**
     * 장바구니에 담긴 상품의 갯수를 수정하는 메서드 입니다.
     *
     * @param cookieValue 쿠기의 Value값을 통해 redis에 저장되어 있습니다.
     * @param productNo 상품번호
     * @param productCount 상품 갯수
     * @author 강명관
     */
    void modifyCart(String cookieValue, Integer productNo, Integer productCount);

    /**
     * RDB에 저장되어있는 회원의 장바구니 리스트를 불러와서 redis에 올려두는 메서드 입니다.
     *
     * @param cookieValue 쿠기의 Value값을 통해 redis에 저장되어 있습니다.
     * @param memberNo 회원번호
     * @author 강명관
     */
    void loadCartProductForMember(String cookieValue, Long memberNo);


}

