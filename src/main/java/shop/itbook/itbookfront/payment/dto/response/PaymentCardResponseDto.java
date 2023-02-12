package shop.itbook.itbookfront.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 카드 결제의 정보를 받아오기 위한 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
public class PaymentCardResponseDto {

    private String cardSerialNo;
    private Long totalAmount;
}
