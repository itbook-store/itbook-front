package shop.itbook.itbookfront.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 로그인 폼에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "loginpage/login";
    }

}
