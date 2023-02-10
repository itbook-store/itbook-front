package shop.itbook.itbookfront.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PaymentCreatedRequestDto {

    private String method;
    private Long amount;
    private String orderId;
    private String orderName;
    private String successUrl;
    private String failUrl;

}
