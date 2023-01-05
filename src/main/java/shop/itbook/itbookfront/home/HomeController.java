package shop.itbook.itbookfront.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author gwanii
 * @since 1.0
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "mainpage/index";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage/index";
    }

    @GetMapping("/adminpage")
    public String adminpage() {
        return "adminpage/index";
    }

    @GetMapping("/test")
    public String test() {
        return "default-layout";
    }
}
