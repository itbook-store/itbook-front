package shop.itbook.itbookfront.coupon.controller.serviceapi;

import java.util.List;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponService {
    void addCoupon(CouponInputRequestDto couponInputRequestDto);

    List<CouponListResponseDto> findCouponList(String couponListUrl);
}
