package shop.itbook.itbookfront.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDto {
    private Long productNo;
    private Long productCnt;
    private Long categoryCouponIssueNo;
    private Long productCouponIssueNo;

}
