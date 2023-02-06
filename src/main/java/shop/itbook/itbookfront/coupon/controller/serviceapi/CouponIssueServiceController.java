package shop.itbook.itbookfront.coupon.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueServiceService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/coupons/coupon-issues")
public class CouponIssueServiceController {

    private final CouponIssueServiceService couponIssueServiceService;
    private static final String DIRECTORY_NAME = "mypage/coupon";


    @GetMapping("/all")
    public String findAllCouponIssueListByMemberId(Model model,
                                                   @AuthenticationPrincipal UserDetailsDto userDetailsDto){

        PageResponse<UserCouponIssueListResponseDto> userCouponIssueList =
            couponIssueServiceService.findUserAllCouponIssueList("/api/coupon-issues/"+ userDetailsDto.getMemberId());

        model.addAttribute("pageResponse", userCouponIssueList);
        model.addAttribute("paginationUrl",
            "/mypage/coupons/coupon-issues/all");
        return DIRECTORY_NAME+"/couponList";
     }

    @GetMapping("/{couponIssueNo}/point-coupon-use")
    public String usePointCoupon(@PathVariable Long couponIssueNo){


        couponIssueServiceService.usePointCouponByUser(
                "/api/coupon-issues/"+ couponIssueNo +"/point-coupon-use");

        return "redirect:/mypage/coupons/coupon-issues/all";
    }
}

