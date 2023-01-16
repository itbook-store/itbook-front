package shop.itbook.itbookfront.login.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String loginForm() {
        return "loginpage/login";
    }

}
