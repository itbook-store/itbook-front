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
        String paymentKey, String orderId, Long amount, Long orderNo, String orderType) {

        return paymentAdaptor.requestApprovePayment(
            new PaymentApproveRequestDto(paymentKey, orderId, amount), orderNo, orderType);
    }

    @Override
    public OrderResponseDto requestCanceledPayment(
        PaymentCanceledRequestDto requestDto, boolean isMemberOrder, boolean isSubscription) {

        PaymentCanceledRequestDto paymentCanceledRequestDto =
            new PaymentCanceledRequestDto(requestDto.getOrderNo(), requestDto.getCancelReason());

        String orderType;
        if (isSubscription) {
            if (isMemberOrder) {
                orderType = "구독회원주문";
            } else {
                orderType = "구독비회원주문";
            }
        } else {
            if (isMemberOrder) {
                orderType = "일반회원주문";
            } else {
                orderType = "일반비회원주문";
            }
        }
        return paymentAdaptor.requestCanceledPayment(paymentCanceledRequestDto, orderType);
    }
}
