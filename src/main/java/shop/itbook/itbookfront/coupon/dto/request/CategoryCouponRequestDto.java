package shop.itbook.itbookfront.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCouponRequestDto {

    private Integer categoryNo;
    private CouponInputRequestDto couponRequestDto;
}
