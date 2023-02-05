package shop.itbook.itbookfront.cart.controller;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

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

    @GetMapping("/general")
    public String getCartListGeneralProduct(@AuthenticationPrincipal Object principal,
                              @CookieValue(value = COOKIE_NAME) Cookie cookie,
                              Model model) {

        if (principal instanceof UserDetailsDto) {
            UserDetailsDto userDetailsDto = (UserDetailsDto) principal;
            List<CartProductDetailsResponseDto> cartListMember = cartService.getCartListMember(
                new CartMemberNoRequestDto(userDetailsDto.getMemberNo())
            ).stream()
                .filter(dto -> !dto.getProductDetailsResponseDto().getIsSubscription())
                .collect(Collectors.toList());

            model.addAttribute(CART_LIST, cartListMember);
            return ROOT_PATH.concat("cart-general");
        }

        List<ProductDetailsResponseDto> cartListAnonymous =
            cartService.getCartListAnonymous(cookie.getValue()).stream()
                .filter(dto -> !dto.getIsSubscription())
                .collect(Collectors.toList());

        model.addAttribute(CART_LIST, cartListAnonymous);


        return ROOT_PATH.concat("cart-general");
    }

    @GetMapping("/subscription")
    public String getCartListSubscriptionProduct(@AuthenticationPrincipal Object principal,
                              @CookieValue(value = COOKIE_NAME) Cookie cookie,
                              Model model) {

        if (principal instanceof UserDetailsDto) {
            UserDetailsDto userDetailsDto = (UserDetailsDto) principal;
            List<CartProductDetailsResponseDto> cartListMember = cartService.getCartListMember(
                new CartMemberNoRequestDto(userDetailsDto.getMemberNo())
            ).stream()
                .filter(dto -> dto.getProductDetailsResponseDto().getIsSubscription())
                .collect(Collectors.toList());

            model.addAttribute(CART_LIST, cartListMember);
            return ROOT_PATH.concat("cart-subscription");
        }
        log.info("비회원 처리");

        List<ProductDetailsResponseDto> cartListAnonymous =
            cartService.getCartListAnonymous(cookie.getValue()).stream()
                .filter(ProductDetailsResponseDto::getIsSubscription)
                .collect(Collectors.toList());

        model.addAttribute(CART_LIST, cartListAnonymous);

        return ROOT_PATH.concat("cart-subscription");
    }
}
