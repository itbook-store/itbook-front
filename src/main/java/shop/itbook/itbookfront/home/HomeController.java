package shop.itbook.itbookfront.home;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shop.itbook.itbookfront.product.dto.response.GetBookListResponseDto;
import shop.itbook.itbookfront.product.service.adminapi.ProductAdminService;

/**
 * @author gwanii
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductAdminService productAdminService;

    @GetMapping("/")
    public String home(Model model) {
        List<GetBookListResponseDto> bookList = productAdminService.getBookList();
        model.addAttribute("bookList", bookList);
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
