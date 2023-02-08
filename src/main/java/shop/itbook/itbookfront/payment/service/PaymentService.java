package shop.itbook.itbookfront.payment.service;

import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;

/**
 * The interface Payment service.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentService {

    /**
     * Gets widget url.
     *
     * @param orderId   the order id
     * @param orderName the order name
     * @param amount    the amount
     * @return the widget url
     */
    String getWidgetUrl(String orderId, String orderName, Long amount);

    /**
     * Request approve api payment response dto . payment data response dto.
     *
     * @param requestDto the request dto
     * @return the payment response dto . payment data response dto
     */
    PaymentResponseDto.PaymentDataResponseDto requestApproveApi(
        PaymentApproveRequestDto requestDto);
}