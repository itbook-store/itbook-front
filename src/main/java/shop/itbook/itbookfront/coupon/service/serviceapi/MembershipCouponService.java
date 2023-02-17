package shop.itbook.itbookfront.coupon.service.serviceapi;

import java.util.List;
import java.util.Map;
import shop.itbook.itbookfront.coupon.dto.response.MembershipCouponResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface MembershipCouponService {
    Map<String, List<MembershipCouponResponseDto>> findAvailableMembershipCoupon(String membershipCouponListUrl);
}
