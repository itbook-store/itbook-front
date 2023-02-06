package shop.itbook.itbookfront.cart.controller;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;

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

    @GetMapping("/add-product")
    public boolean productAddToCart(@AuthenticationPrincipal Object principal,
                                 @CookieValue(value = COOKIE_NAME)Cookie cookie,
                                 @RequestParam(value = "productNo")Integer productNo) {

        if (checkTypeUserDetailsDto(principal)) {

            UserDetailsDto userDetailsDto = getUserDetailsDto(principal);
            CartMemberRequestDto cartMemberRequestDto = new CartMemberRequestDto(
                userDetailsDto.getMemberNo().intValue(),
                productNo
            );

            return cartService.addProductMemberToCart(cartMemberRequestDto);
        }

        return cartService.addProductAnonymousToCart(cookie.getValue(), productNo);
    }

    @GetMapping("/delete-product")
    public void productDeleteToCart(@AuthenticationPrincipal Object principal,
                                    @CookieValue(value = COOKIE_NAME) Cookie cookie,
                                    @RequestParam(value = "productNo") Integer productNo) {

        if (checkTypeUserDetailsDto(principal)) {
            UserDetailsDto userDetailsDto = getUserDetailsDto(principal);
            CartMemberRequestDto cartMemberRequestDto = new CartMemberRequestDto(
                userDetailsDto.getMemberNo().intValue(),
                productNo
            );

            cartService.deleteProductMemberToCart(cartMemberRequestDto);
            return;
        }

        cartService.deleteProductAnonymousToCart(cookie.getValue(), productNo);
    }

    @GetMapping("/delete/all-product")
    public void productDeleteAllToCart(@AuthenticationPrincipal Object principal,
                                       @CookieValue(value = COOKIE_NAME) Cookie cookie) {


        if (checkTypeUserDetailsDto(principal)) {
            UserDetailsDto userDetailsDto = getUserDetailsDto(principal);

            cartService.deleteAllProductMemberToCart(
                new CartMemberNoRequestDto(userDetailsDto.getMemberNo())
            );
            return;
        }

        cartService.deleteAllProductAnonymousToCart(cookie.getValue());
    }

    @PostMapping("/change/product-count")
    public void productCountChangeInCart(Authentication authentication,
                                         @RequestBody CartMemberRequestDto cartMemberRequestDto) {

        if (!authentication.isAuthenticated()) {
            return;
        }

        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();

        if (!checkValidMember(cartMemberRequestDto, userDetailsDto)) {
            return;
        }

        cartService.modifyProductCountToCart(cartMemberRequestDto);

    }

    private static boolean checkValidMember(CartMemberRequestDto cartMemberRequestDto,
                                    UserDetailsDto userDetailsDto) {
        return userDetailsDto.getMemberNo()
            .equals(Long.valueOf(cartMemberRequestDto.getMemberNo()));
    }

    private boolean checkTypeUserDetailsDto(Object principal) {
        return principal instanceof UserDetailsDto;
    }

    private UserDetailsDto getUserDetailsDto(Object principal) {
        return (UserDetailsDto) principal;
    }


}
