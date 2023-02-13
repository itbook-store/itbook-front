package shop.itbook.itbookfront.payment.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.payment.dto.response.OrderResponseDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentErrorResponseDto;
import shop.itbook.itbookfront.payment.exception.InvalidPaymentException;
import shop.itbook.itbookfront.payment.service.PaymentService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping(value = "/orders/success/{orderNo}", params = {"paymentKey", "orderId", "amount"})
    public String successHandler(@RequestParam String paymentKey, @RequestParam String orderId,
                                 @RequestParam Long amount, @PathVariable Long orderNo) {

        OrderResponseDto responseDto =
            paymentService.requestApprovePayment(paymentKey, orderId, amount, orderNo);

        if (Objects.isNull(responseDto)) {
            throw new InvalidPaymentException();
        }

        if (!responseDto.getTotalAmount().equals(amount)) {
            // requestPayment() 메서드에 담아 보낸 amount 값과 successUrl로 돌아온 amount 값이 같은지 확인
            throw new InvalidPaymentException();
        }
        return "redirect:/orders/completion/" + responseDto.getOrderNo();
    }

    @GetMapping(value = "/orders/fail/{orderNo}", params = {"code", "message", "orderId"})
    public String failureHandler(@RequestParam String code, @RequestParam String message,
                                 @RequestParam String orderId, @PathVariable Long orderNo) {
        throw new InvalidPaymentException(message);
    }

}
