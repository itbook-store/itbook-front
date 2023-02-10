package shop.itbook.itbookfront.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 정재원
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Setter
public class OrderPaymentDto {
    private Long orderNo;
    private String orderId;
    private String orderName;
    private Long amount;
}
