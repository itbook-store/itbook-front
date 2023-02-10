package shop.itbook.itbookfront.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderNoResponseDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentErrorResponseDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.service.PaymentService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

//    @PostMapping("/payment/paymentWidget")
//    public String getPaymentWidget(@ModelAttribute OrderAddRequestDto orderAddRequestDto)
//        throws JsonProcessingException {
//
//        String orderId = orderAddRequestDto.getOrderId();
//        String orderName = orderAddRequestDto.getOrderName();
//        Long amount = orderAddRequestDto.getAmount();
//
//        String widgetUrl = paymentService.getWidgetUrl(orderId, orderName, amount);
//
//        return "redirect:" + widgetUrl;
//    }


    @GetMapping(value = "/orders/success/{orderNo}", params = {"paymentKey", "orderId", "amount"})
    public String successHandler(@RequestParam String paymentKey, @RequestParam String orderId,
                                 @RequestParam Long amount, @PathVariable Long orderNo) {

        // requestPayment() 메서드에 담아 보낸 amount 값과 successUrl로 돌아온 amount 값이 같은지 확인해보기
        // 값이 다르면 결제 요청을 다시 하기

        OrderNoResponseDto responseDto =
            paymentService.requestApprovePayment(paymentKey, orderId, amount, orderNo);
        if (Objects.isNull(responseDto)) {
//            return "redirect:" + FAIL_URL;
        }

        return "redirect:/orders/completion/" + responseDto.getOrderNo();
    }

    @GetMapping(value = "/orders/fail/{orderNo}", params = {"code", "message", "orderId"})
    public String failureHandler(@RequestParam String code, @RequestParam String message,
                                 @RequestParam String orderId, @PathVariable Long orderNo) {
        PaymentErrorResponseDto
            requestDto = new PaymentErrorResponseDto(code, message);

        return "redirect:/";
    }

//    @GetMapping("/payment/cancel/{orderNo}")
//    public String getCancelPaymentForm(@PathVariable Long orderNo, Model model) {
//        model.addAttribute("orderNo", orderNo);
//        return "mypage/order/select-cancel-reason";
//
//    }

    @PostMapping("/payment/cancel/{orderNo}")
    public String requestCancelPayment(@PathVariable Long orderNo,
                                       @RequestParam String canceledReason) {
        paymentService.requestCanceledPayment(orderNo, canceledReason);
        return "redirect:/mypage/list";
    }

}
