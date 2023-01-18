package shop.itbook.itbookfront.home;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;

/**
 * @author gwanii
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest httpServletRequest) {
        List<CategoryListResponseDto> categoryList = categoryService.findCategoryList("/api/categories");

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);

        String remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
        log.info("########## 브라우저 ip : " + remoteAddr);

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
