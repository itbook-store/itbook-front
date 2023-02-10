package shop.itbook.itbookfront.payment.dto.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentErrorResponseDto implements Serializable {
    private String statusCode;
    private String code;
    private String message;

    public PaymentErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
