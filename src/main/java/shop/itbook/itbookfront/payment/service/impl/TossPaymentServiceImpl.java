package shop.itbook.itbookfront.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.payment.adaptor.PaymentAdaptor;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderResponseDto;
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
    public OrderResponseDto requestApprovePayment(
        String paymentKey, String orderId, Long amount, Long orderNo) {

        return paymentAdaptor.requestApprovePayment(
            new PaymentApproveRequestDto(paymentKey, orderId, amount), orderNo);
    }

    @Override
    public OrderResponseDto requestCanceledPayment(
        PaymentCanceledRequestDto requestDto) {

        PaymentCanceledRequestDto paymentCanceledRequestDto =
            new PaymentCanceledRequestDto(requestDto.getOrderNo(), requestDto.getCancelReason());

        return paymentAdaptor.requestCanceledPayment(paymentCanceledRequestDto);
    }
}
