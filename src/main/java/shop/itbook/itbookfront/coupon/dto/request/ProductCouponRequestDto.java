package shop.itbook.itbookfront.coupon.dto.request;

import javax.validation.constraints.NotNull;
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
public class ProductCouponRequestDto {
    private Long productNo;
    private CouponInputRequestDto couponRequestDto;
}
