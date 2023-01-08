package shop.itbook.itbookfront.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 회원가입 폼에 대한 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Controller
public class SignupController{

    @GetMapping("/signup")
    public String signupForm() {
        return "signuppage/signup";
    }
}
