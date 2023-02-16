package shop.itbook.itbookfront.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 정재원
 * @since 1.0
 */
@Setter
@AllArgsConstructor
@Getter
public class OrderBeforePaymentResponseDto<T> {

    Boolean isSuccess;
    T result;
}
