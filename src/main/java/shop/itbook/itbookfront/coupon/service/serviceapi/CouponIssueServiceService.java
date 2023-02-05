package shop.itbook.itbookfront.coupon.service.serviceapi;

import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueServiceService {
    PageResponse<UserCouponIssueListResponseDto> findUserAllCouponIssueList(
        String userAllCouponIssueUrl);

    void usePointCouponByUser(String usePointCouponIssueUrl);
}
