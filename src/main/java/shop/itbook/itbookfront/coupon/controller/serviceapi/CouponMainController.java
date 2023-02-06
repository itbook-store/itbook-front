package shop.itbook.itbookfront.coupon.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponMainController {

    private final CouponIssueService couponIssueService;
    private static final String DIRECTORY_NAME = "mainpage/coupon";

    @GetMapping("/month")
    public String couponOfMonth(Model model) {
        List<CouponListResponseDto> couponList = couponIssueService.getCouponsByCouponType(
            String.format("/api/admin/coupons/list/all/%s","이달의쿠폰예약형"));

        model.addAttribute("couponList", couponList);
        return DIRECTORY_NAME+"/coupon-event";
    }

    @GetMapping("/download")
    public String couponOfMonth(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                @RequestParam("couponNo") Long couponNo) {
        couponIssueService.addCouponIssueByCouponType(
            String.format("/api/coupon-issues/%d/%d/add",couponNo,userDetailsDto.getMemberNo()));
        return "redirect:/coupons/month";
    }
}
