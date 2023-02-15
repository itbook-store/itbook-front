package shop.itbook.itbookfront.cart.controller;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.util.List;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.cart.dto.response.CartAddResponseDto;
import shop.itbook.itbookfront.cart.service.CartService;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.product.exception.ProductNotFoundException;

/**
 * 장바구니에 대한 비동기 통신을 하는 컨트롤러입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/cart")
public class CartAsyncController {

    private final CartService cartService;

    @PostMapping("/add-product")
    public CartAddResponseDto productAddToCart(@CookieValue(value = COOKIE_NAME) Cookie cookie,
                                               @RequestParam(value = "productNo") Integer productNo) {

        CartAddResponseDto cartAddResponseDto;
        try {
            cartAddResponseDto = cartService.addCartProduct(cookie.getValue(), productNo);
        } catch (BadRequestException e) {
            cartAddResponseDto = new CartAddResponseDto(false, "장바구니 담는데 실패하였습니다.");
        }

        return cartAddResponseDto;
    }

    @GetMapping("/delete-product")
    public void productDeleteToCart(@CookieValue(value = COOKIE_NAME) Cookie cookie,
                                    @RequestParam(value = "productNo") Integer productNo) {

        cartService.deleteCartProduct(cookie.getValue(), productNo);
    }
//
    @PostMapping("/delete/all-product")
    public void productDeleteAllToCart(@CookieValue(value = COOKIE_NAME) Cookie cookie,
                                       @RequestParam(value = "productNo") List<Integer> productNoList) {

        cartService.deleteAllCartProduct(cookie.getValue(), productNoList);

    }

    @PostMapping("/change/product-count")
    public void productCountChangeInCart(@CookieValue(value = COOKIE_NAME) Cookie cookie,
                                         @RequestParam(value = "productNo") Integer productNo,
                                         @RequestParam(value = "productCount") Integer productCount) {
        cartService.modifyCart(cookie.getValue(), productNo, productCount);
    }

}
