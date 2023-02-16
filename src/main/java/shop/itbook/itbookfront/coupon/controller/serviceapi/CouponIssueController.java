package shop.itbook.itbookfront.coupon.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/coupons/coupon-issues")
public class CouponIssueController {

    private final CouponIssueService couponIssueService;
    private static final String DIRECTORY_NAME = "mypage/coupon";


    @GetMapping("/all")
    public String findAllCouponIssueListByMemberId(Model model,
                                                   @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                                   @PageableDefault Pageable pageable){

        PageResponse<UserCouponIssueListResponseDto> userCouponIssueList =
            couponIssueService.findUserAllCouponIssueList(String.format("/api/coupon-issues/%d?page=%d&size=%d",
                userDetailsDto.getMemberNo(),pageable.getPageNumber(), pageable.getPageSize()));

        model.addAttribute("pageResponse", userCouponIssueList);
        model.addAttribute("paginationUrl",
            "/mypage/coupons/coupon-issues/all");
        return DIRECTORY_NAME+"/couponList";
     }

    @GetMapping("/{couponIssueNo}/point-coupon-use")
    public String usePointCoupon(@PathVariable Long couponIssueNo, RedirectAttributes redirectAttributes){

        try {

        couponIssueService.usePointCouponByUser(
                "/api/coupon-issues/"+ couponIssueNo +"/point-coupon-use");
        } catch (BadRequestException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());

        }

        return "redirect:/mypage/coupons/coupon-issues/all";
    }
}

