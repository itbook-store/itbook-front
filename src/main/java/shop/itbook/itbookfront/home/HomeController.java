package shop.itbook.itbookfront.home;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.member.dto.request.MemberSocialRequestDto;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.service.ProductService;
import shop.itbook.itbookfront.product.service.impl.ProductServiceImpl;

/**
 * @author gwanii
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ProductService productService;

    private final CategoryService categoryService;

    private final MemberService memberService;

    public static final Integer SIZE_OF_ALL_CONTENT = Integer.MAX_VALUE;
    public static final Integer PAGE_OF_ALL_CONTENT = 0;

    @GetMapping("/")
    public String home(Model model,
                       @ModelAttribute("memberSocialRequestDto")
                       MemberSocialRequestDto memberSocialRequestDto,
                       HttpServletRequest httpServletRequest,
                       @AuthenticationPrincipal
                       UserDetailsDto userDetailsDto) throws IOException {

        if (Objects.nonNull(userDetailsDto) &&
            memberService.findMemberInfo(userDetailsDto.getMemberId()).getPhoneNumber()
                .equals(userDetailsDto.getMemberId())) {

            model.addAttribute("memberInfo",
                memberService.findMemberInfo(userDetailsDto.getMemberId()));

            return "signuppage/oauth-signup";
        }


        PageResponse<CategoryListResponseDto> pageResponse =
            categoryService.findCategoryList(String.format("/api/categories?page=%d&size=%d",
                PAGE_OF_ALL_CONTENT, SIZE_OF_ALL_CONTENT));

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(pageResponse.getContent());
        model.addAttribute("mainCategoryList", mainCategoryList);


        List<ProductTypeResponseDto> productTypeList = productService.findProductTypeList(
            "/api/products/product-types?page=0&size=" + Integer.MAX_VALUE).getContent();
        model.addAttribute("productTypeList", productTypeList);
        

        List<ProductDetailsResponseDto> newBooks = productService.getBooksByProductTypes(1);
        model.addAttribute("newBooks", newBooks);

        List<ProductDetailsResponseDto> discountBooks = productService.getBooksByProductTypes(2);
        model.addAttribute("discountBooks", discountBooks);

        List<ProductDetailsResponseDto> bestSeller = productService.getBooksByProductTypes(3);
        model.addAttribute("bestSeller", bestSeller);

        List<ProductDetailsResponseDto> recommendation = productService.getBooksByProductTypes(4);
        model.addAttribute("recommendationList", recommendation);

        List<ProductDetailsResponseDto> popularBooks = productService.getBooksByProductTypes(5);
        model.addAttribute("popularBooks", popularBooks);

        String remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
        log.info("########## 브라우저 ip : " + remoteAddr);


        return "mainpage/index";
    }

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                         Model model) {

        Long recentlyPoint =
            memberService.findMemberRecentlyPoint(userDetailsDto.getMemberNo()).getRemainedPoint();
        model.addAttribute("memberId", userDetailsDto.getMemberId());
        model.addAttribute("recentlyPoint", recentlyPoint);

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
