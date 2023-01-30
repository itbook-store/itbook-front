package shop.itbook.itbookfront.home;

import java.io.IOException;
import java.util.List;
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
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.product.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.service.impl.ProductServiceImpl;

/**
 * @author gwanii
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ProductServiceImpl productServiceImpl;

    private final CategoryService categoryService;
    public static final Integer CATEGORY_ALL_RECEIVE_SIZE = Integer.MAX_VALUE;
    public static final Integer CATEGORY_ALL_RECEIVE_PAGE = 0;


    @GetMapping("/")
    public String home(Model model, HttpServletRequest httpServletRequest) throws IOException {

        PageResponse<CategoryListResponseDto> pageResponse =
            categoryService.findCategoryList(String.format("/api/categories?page=%d&size=%d",
                CATEGORY_ALL_RECEIVE_PAGE, CATEGORY_ALL_RECEIVE_SIZE));

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(pageResponse.getContent());
        model.addAttribute("mainCategoryList", mainCategoryList);

        String remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
        log.info("########## 브라우저 ip : " + remoteAddr);

        List<ProductDetailsResponseDto> bookList = productServiceImpl.getProductList(true);
        model.addAttribute("productList", bookList);
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

    @GetMapping("/template")
    public String template() {
        return "mainpage/template/index";
    }
}
