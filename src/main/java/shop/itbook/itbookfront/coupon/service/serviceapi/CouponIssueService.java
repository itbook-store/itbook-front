package shop.itbook.itbookfront.coupon.service.serviceapi;

import java.util.List;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    PageResponse<UserCouponIssueListResponseDto> findUserAllCouponIssueList(
        String userAllCouponIssueUrl);

    void usePointCouponByUser(String usePointCouponIssueUrl);

    Long addCouponIssueByCouponType(String couponIssueByCouponTypeUrl);

    List<CouponListResponseDto> getCouponsByCouponType(String couponsByCouponTypeUrl);
}
