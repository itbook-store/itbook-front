package shop.itbook.itbookfront.coupon.service.adminapi;

import java.util.List;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponService {
    Long addCoupon(CouponInputRequestDto couponInputRequestDto);

    Long addOrderTotalCoupon(CouponInputRequestDto couponInputRequestDto);

    Long addCategoryCoupon(CouponInputRequestDto couponInputRequestDto);

    Long addProductCoupon(CouponInputRequestDto couponInputRequestDto);

    PageResponse<AdminCouponListResponseDto> findCouponList(String couponListUrl);

    void addMembershipCoupon(Integer membership, Long couponNo);
}
