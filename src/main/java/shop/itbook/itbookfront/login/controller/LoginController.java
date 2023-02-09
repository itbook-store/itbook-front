package shop.itbook.itbookfront.login.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 로그인 폼에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Authentication authentication,
                            RedirectAttributes redirectAttributes,
                            @RequestParam(value = "error", required = false)String errorParam) {
        if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
            return "redirect:/";
        }

        if (Objects.nonNull(errorParam)) {
            redirectAttributes.addFlashAttribute("failMessage", errorParam);
            return "redirect:/login";
        }

        return "loginpage/login";
    }



}
