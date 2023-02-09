package shop.itbook.itbookfront.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.payment.adaptor.PaymentAdaptor;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderNoResponseDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookfront.payment.service.PaymentService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TossPaymentServiceImpl implements PaymentService {
    private final PaymentAdaptor paymentAdaptor;

    @Override
    public String getWidgetUrl(String orderId, String orderName, Long amount) {

        return paymentAdaptor.requestWidget(orderId, orderName, amount);
    }

    @Override
    public OrderNoResponseDto requestApprovePayment(
        PaymentApproveRequestDto requestDto) {
        OrderNoResponseDto responseDto =
            paymentAdaptor.requestApprovePayment(requestDto);

        return responseDto;
    }

    @Override
    public OrderNoResponseDto requestCanceledPayment(
        Long orderNo, String canceledReason) {

        PaymentCanceledRequestDto paymentCanceledRequestDto =
            new PaymentCanceledRequestDto(orderNo, canceledReason);
        OrderNoResponseDto responseDto =
            paymentAdaptor.requestCanceledPayment(paymentCanceledRequestDto);
        return responseDto;
    }
}
