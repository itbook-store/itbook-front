package shop.itbook.itbookfront.cart.controller;

import static shop.itbook.itbookfront.cart.util.CartConstant.COOKIE_NAME;

import java.util.List;
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
import shop.itbook.itbookfront.cart.dto.response.CartResponseDto;
import shop.itbook.itbookfront.cart.dto.resquest.CartMemberNoRequestDto;
import shop.itbook.itbookfront.cart.service.CartService;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private static final String ROOT_PATH = "mainpage/cart/";

    @GetMapping
    public String getCartList(@AuthenticationPrincipal Object principal,
                              @CookieValue(value = COOKIE_NAME) Cookie cookie,
                              Model model) {

        log.info("cookie.getName() {}", cookie.getName());
        log.info("cookie.getValue() {}", cookie.getValue());

        if (principal instanceof UserDetailsDto) {
            log.info("회원 처리");
            UserDetailsDto userDetailsDto = (UserDetailsDto) principal;
            List<CartResponseDto> cartListMember = cartService.getCartListMember(
                new CartMemberNoRequestDto(userDetailsDto.getMemberNo()));

            model.addAttribute("cartList", cartListMember);
        }

        log.info("비회원 처리");
        List<CartResponseDto> cartListAnonymous =
            cartService.getCartListAnonymous(cookie.getValue());

        model.addAttribute("cartList", cartListAnonymous);


        return ROOT_PATH.concat("cart");
    }
}
