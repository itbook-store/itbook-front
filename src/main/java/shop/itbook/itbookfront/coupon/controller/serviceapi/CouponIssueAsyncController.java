package shop.itbook.itbookfront.coupon.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.coupon.dto.response.OrderCouponSimpleListResponseDto;
import shop.itbook.itbookfront.coupon.service.adminapi.CouponService;
import shop.itbook.itbookfront.coupon.service.serviceapi.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/async/coupon-issues")
public class CouponIssueAsyncController {

    private final CouponIssueService couponIssueService;

    @GetMapping("/{productNo}")
    public List<OrderCouponSimpleListResponseDto> findProductCategoryCouponIssueList(
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        @PathVariable Long productNo) {

        return couponIssueService.getProductCategoryCoupon(
            String.format("/api/coupon-issues/%d/order/%d", userDetailsDto.getMemberNo(),
                productNo));
    }

    @GetMapping("/total")
    public List<OrderCouponSimpleListResponseDto> findTotalCouponIssueList(
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        return couponIssueService.getProductCategoryCoupon(
            String.format("/api/coupon-issues/%d/order/total", userDetailsDto.getMemberNo()));
    }
}
