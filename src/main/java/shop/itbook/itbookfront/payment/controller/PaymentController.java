package shop.itbook.itbookfront.payment.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.payment.dto.response.OrderResponseDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentErrorResponseDto;
import shop.itbook.itbookfront.payment.exception.InvalidPaymentException;
import shop.itbook.itbookfront.payment.service.PaymentService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping(value = "/orders/success/{orderNo}"
        , params = {"paymentKey", "orderId", "amount", "orderType"})
    public String successHandler(@RequestParam String paymentKey, @RequestParam String orderId,
                                 @RequestParam Long amount, RedirectAttributes redirectAttributes,
                                 @RequestParam String orderType, @PathVariable Long orderNo) {
        OrderResponseDto responseDto;
        try {
            responseDto =
                paymentService.requestApprovePayment(paymentKey, orderId, amount, orderNo,
                    orderType);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return "redirect:/";
        }
    
        return "redirect:/orders/completion/" + responseDto.getOrderNo() + "?orderType=" +
            orderType;
    }

    @GetMapping(value = "/orders/fail/{orderNo}", params = {"code", "message", "orderId"})
    public String failureHandler(@RequestParam String code, @RequestParam String message,
                                 @RequestParam String orderId, @PathVariable Long orderNo,
                                 RedirectAttributes redirectAttributes) {

        log.error("payment failure! code: {}, message: {}, orderId: {}, orderNo: {}",
            code, message, orderId, orderNo);
        redirectAttributes.addFlashAttribute("failMessage", "결제에 실패하였습니다.");

        return "redirect:/";
    }

}
