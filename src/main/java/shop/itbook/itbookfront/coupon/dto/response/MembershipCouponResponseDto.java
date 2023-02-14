package shop.itbook.itbookfront.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipCouponResponseDto {
    private String membershipGrade;
    private ServiceCouponListResponseDto coupon;
}
