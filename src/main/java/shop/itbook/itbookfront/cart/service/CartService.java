package shop.itbook.itbookfront.cart.service;

import java.util.List;
import shop.itbook.itbookfront.cart.dto.response.CartResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;

/**
 * @author 강명관
 * @since 1.0
 */

public interface CartService {

    boolean addProductAnonymousToCart(String cookieValue, Integer productNo);

    boolean addProductMemberToCart(CartMemberRequestDto cartMemberRequestDto);

    List<CartResponseDto> getCartListAnonymous(String cookieValue);

    List<CartResponseDto> getCartListMember(CartMemberNoRequestDto cartMemberNoRequestDto);

    void deleteProductAnonymousToCart(String cookieValue, Integer productNo);

    void deleteAllProductAnonymousToCart(String cookieValue);

    void deleteProductMemberToCart(CartMemberRequestDto cartMemberRequestDto);

    void deleteAllProductMemberToCart(CartMemberNoRequestDto cartMemberNoRequestDto);
}

