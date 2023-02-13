package shop.itbook.itbookfront.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubscriptionRequestDto {
    private Integer subscriptionPeriod;
}
