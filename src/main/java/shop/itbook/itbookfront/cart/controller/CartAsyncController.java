package shop.itbook.itbookfront.cart.controller;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/cart")
public class CartAsyncController {

    private final CartService cartService;

    @GetMapping("/add-product")
    public boolean productAddToCart(@AuthenticationPrincipal Object principal,
                                 @CookieValue(value = COOKIE_NAME)Cookie cookie,
                                 @RequestParam(value = "productNo")Integer productNo) {

        log.info("cookie.getValue {}", cookie.getValue());
        log.info("productNo {}", productNo);

        if (checkTypeUserDetailsDto(principal)) {
            log.info("회원 처리");

            UserDetailsDto userDetailsDto = getUserDetailsDto(principal);
            CartMemberRequestDto cartMemberRequestDto = new CartMemberRequestDto(
                userDetailsDto.getMemberNo(),
                productNo
            );

            return cartService.addProductMemberToCart(cartMemberRequestDto);
        }

        log.info("비회원 처리");
        return cartService.addProductAnonymousToCart(cookie.getValue(), productNo);

    }

    @GetMapping("/delete-product")
    public void productDeleteToCart(@AuthenticationPrincipal Object principal,
                                    @CookieValue(value = COOKIE_NAME) Cookie cookie,
                                    @RequestParam(value = "productNo") Integer productNo) {

        if (checkTypeUserDetailsDto(principal)) {
            log.info("회원 처리");

            UserDetailsDto userDetailsDto = getUserDetailsDto(principal);
            CartMemberRequestDto cartMemberRequestDto = new CartMemberRequestDto(
                userDetailsDto.getMemberNo(),
                productNo
            );

            cartService.deleteProductMemberToCart(cartMemberRequestDto);
            return;
        }

        log.info("비회원 처리");
        cartService.deleteProductAnonymousToCart(cookie.getValue(), productNo);
    }

    @GetMapping("/delete/all-product")
    public void productDeleteAllToCart(@AuthenticationPrincipal Object principal,
                                       @CookieValue(value = COOKIE_NAME) Cookie cookie) {


        if (checkTypeUserDetailsDto(principal)) {
            log.info("회원 처리");
            UserDetailsDto userDetailsDto = getUserDetailsDto(principal);

            cartService.deleteAllProductMemberToCart(
                new CartMemberNoRequestDto(userDetailsDto.getMemberNo())
            );
            return;
        }

        log.info("비회원 처리");
        cartService.deleteAllProductAnonymousToCart(cookie.getValue());
    }

    private boolean checkTypeUserDetailsDto(Object principal) {
        return principal instanceof UserDetailsDto;
    }

    private UserDetailsDto getUserDetailsDto(Object principal) {
        return (UserDetailsDto) principal;
    }


}
