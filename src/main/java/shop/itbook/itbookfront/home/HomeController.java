package shop.itbook.itbookfront.home;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.itbook.itbookfront.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookfront.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookfront.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookfront.member.service.serviceapi.MemberService;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.service.BookService;
import shop.itbook.itbookfront.product.service.ProductService;

/**
 * @author gwanii
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ProductService productService;
    private final BookService bookService;

    private final CategoryService categoryService;
    private final MemberAdminService memberAdminService;

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
            memberService.findMember(userDetailsDto.getMemberNo()).getPhoneNumber()
                .equals(userDetailsDto.getMemberId())) {

            model.addAttribute("memberInfo",
                memberService.findMember(userDetailsDto.getMemberNo()));

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


        List<ProductDetailsResponseDto> newBooks = bookService.getProductTypeList(1);
        model.addAttribute("newBooks", newBooks);

        List<ProductDetailsResponseDto> discountBooks = bookService.getProductTypeList(2);
        model.addAttribute("discountBooks", discountBooks);

        List<ProductDetailsResponseDto> bestSeller = bookService.getProductTypeList(3);
        model.addAttribute("bestSeller", bestSeller);

        List<ProductDetailsResponseDto> recommendation;
        if (Objects.nonNull(userDetailsDto)) {
            recommendation =
                bookService.getPersonalRecommendationList(userDetailsDto.getMemberNo());
        } else {
            recommendation = bookService.getProductTypeList(4);
        }
        model.addAttribute("recommendationList", recommendation);

        List<ProductDetailsResponseDto> popularBooks = bookService.getProductTypeList(5);
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
    public String adminpage(Model model) {
        MemberCountResponseDto count1 = memberAdminService.countMemberByMemberStatus();

        model.addAttribute("count1", count1);
        model.addAttribute("normalCount", count1.getMemberCount() -
            (count1.getBlockMemberCount() + count1.getWithdrawMemberCount()));

        MemberCountByMembershipResponseDto count2 = memberAdminService.countMemberByMembership();
        model.addAttribute("count2", count2);
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
