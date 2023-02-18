package shop.itbook.itbookfront.coupon.service.adminapi;

import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueAdminService {
    PageResponse<AdminCouponIssueListResponseDto> findAllCouponIssueList(String couponIssueListUrl);
}
