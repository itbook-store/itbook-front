package shop.itbook.itbookfront.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderNoResponseDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;

/**
 * The interface Payment service.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentService {

    /**
     * 결제창을 호출합니다.
     *
     * @param orderId   the order id
     * @param orderName the order name
     * @param amount    the amount
     * @return the widget url
     * @throws JsonProcessingException the json processing exception
     */
    String getWidgetUrl(String orderId, String orderName, Long amount)
        throws JsonProcessingException;

    /**
     * 샵 서버를 통해 토스에 결제 승인 요청을 하는 기능을 담당합니다.
     *
     * @return the payment response dto . payment data response dto
     */
    OrderNoResponseDto requestApprovePayment(
        String paymentKey, String orderId, Long amount);

    /**
     * 샵 서버를 통해 토스에 결제 취소 요청을 하는 기능을 담당합니다.
     *
     * @param orderNo        the order no
     * @param canceledReason the canceled reason
     * @return the order no response dto
     */
    OrderNoResponseDto requestCanceledPayment(
        Long orderNo, String canceledReason);
}