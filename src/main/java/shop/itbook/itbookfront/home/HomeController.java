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
    public String myPage() {
        return "mypage/index";
    }

    @GetMapping("/adminpage")
    public String adminPage() {
        return "adminpage/index";
    }

    @GetMapping("/adminproduct")
    public String adminProductPage() {
        return "adminpage/product/product-management";
    }

    @GetMapping("/adminmember")
    public String adminMemberPage() {
        return "adminpage/member/member-management";
    }

    @GetMapping("/adminpoint")
    public String adminPointPage() {
        return "adminpage/point/point-management";
    }

    @GetMapping("/test")
    public String test() {
        return "default-layout";
    }
}
