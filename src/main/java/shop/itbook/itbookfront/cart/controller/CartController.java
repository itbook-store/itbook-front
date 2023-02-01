package shop.itbook.itbookfront.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private static final String ROOT_PATH = "mainpage/cart/";
    @GetMapping
    public String cart() {
        return ROOT_PATH.concat("cart");
    }
}
