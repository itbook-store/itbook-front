package shop.itbook.itbookfront.cart.controller;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.cart.annotation.Cart;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * 장바구니에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    private static final String CART_LIST = "cartList";
    private static final String ROOT_PATH = "mainpage/cart/";

    @Cart
    @GetMapping("/general")
    public String getCartListGeneralProduct(@CookieValue(value = COOKIE_NAME) Cookie cookie,
                                            Model model) {

        List<Object> cartList = cartService.getCartList(cookie.getValue());
        List<CartProductDetailsResponseDto> collect = cartList.stream()
            .map(CartProductDetailsResponseDto.class::cast)
            .filter(dto -> !dto.getProductDetailsResponseDto().getIsSubscription())
            .collect(Collectors.toList());

        model.addAttribute(CART_LIST, collect);

        return ROOT_PATH.concat("cart-general");
    }

    @Cart
    @GetMapping("/subscription")
    public String getCartListSubscriptionProduct(@CookieValue(value = COOKIE_NAME) Cookie cookie,
                                                 Model model) {

        List<Object> cartList = cartService.getCartList(cookie.getValue());
        List<CartProductDetailsResponseDto> collect = cartList.stream()
            .map(CartProductDetailsResponseDto.class::cast)
            .filter(dto -> dto.getProductDetailsResponseDto().getIsSubscription())
            .collect(Collectors.toList());

        model.addAttribute(CART_LIST, collect);

        return ROOT_PATH.concat("cart-subscription");
    }
}
