package shop.itbook.itbookfront.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 폼에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@RestController
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "loginpage/login";
    }

}
