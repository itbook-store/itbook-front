package shop.itbook.itbookfront.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderResponseDto;
import shop.itbook.itbookfront.payment.service.PaymentService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class PaymentAsyncController {
    private final PaymentService paymentService;

    @PostMapping("async/payment/cancel")
    public OrderResponseDto requestCancelPayment(
        @RequestBody PaymentCanceledRequestDto paymentCanceledRequestDto,
        RedirectAttributes redirectAttributes) {
        OrderResponseDto orderResponseDto = null;
        try {
            orderResponseDto =
                paymentService.requestCanceledPayment(paymentCanceledRequestDto);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return orderResponseDto;
    }
}
