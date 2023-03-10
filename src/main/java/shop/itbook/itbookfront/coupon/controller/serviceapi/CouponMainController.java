package shop.itbook.itbookfront.coupon.controller.serviceapi;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.category.model.MainCategory;
import shop.itbook.itbookfront.category.service.CategoryService;
import shop.itbook.itbookfront.category.util.CategoryUtil;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueService;
import shop.itbook.itbookfront.coupon.service.serviceapi.MembershipCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponMainController {

    private final CouponIssueService couponIssueService;
    private final MembershipCouponService membershipCouponService;
    private final CategoryService categoryService;
    private static final String DIRECTORY_NAME = "mainpage/coupon";

    @GetMapping("/month")
    public String couponOfMonth(Model model) {

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryService.findCategoryListForUser());
        model.addAttribute("mainCategoryList", mainCategoryList);

        List<CouponListResponseDto> couponList = couponIssueService.getCouponsByCouponType(
            String.format("/api/admin/coupons/list/all/%s", "이달의쿠폰예약형"));

        model.addAttribute("couponList", couponList);
        return DIRECTORY_NAME + "/monthly-coupon-event";
    }

    @GetMapping("/membership")
    public String couponOfMembership(Model model) {

        List<MainCategory> mainCategoryList =
            CategoryUtil.getMainCategoryList(categoryService.findCategoryListForUser());
        model.addAttribute("mainCategoryList", mainCategoryList);

        Map<String, List<MembershipCouponResponseDto>> membershipCouponList =
            membershipCouponService.findAvailableMembershipCoupon(
                "/api/membership-coupons/list");

        model.addAttribute("membershipCouponList", membershipCouponList);
        return DIRECTORY_NAME + "/membership-coupon-event";
    }

    @GetMapping("/month/download")
    public String monthCouponDownloadPage(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                          @RequestParam("couponNo") Long couponNo,
                                          RedirectAttributes redirectAttributes,
                                          HttpServletRequest request) {
        if (Objects.isNull(userDetailsDto)) {
            redirectAttributes.addFlashAttribute("failMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }
        try {
            couponIssueService.addCouponIssueByCouponType(
                String.format("/api/coupon-issues/%d/%d/add", couponNo,
                    userDetailsDto.getMemberNo()));
        } catch (BadRequestException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:" + request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/membership/download")
    public String membershipCouponDownloadPage(
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @RequestParam("couponNo") Long couponNo,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request) {
        if (Objects.isNull(userDetailsDto)) {
            redirectAttributes.addFlashAttribute("failMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        try {

            couponIssueService.addCouponIssueByCouponType(
                String.format("/api/membership-coupons/%d/%d/add", couponNo,
                    userDetailsDto.getMemberNo()));
        } catch (BadRequestException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:" + request.getHeader("Referer");
        }
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:" + request.getHeader("Referer");
    }

}
