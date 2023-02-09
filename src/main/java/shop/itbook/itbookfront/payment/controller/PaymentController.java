package shop.itbook.itbookfront.payment.controller;

import static shop.itbook.itbookfront.payment.adaptor.PaymentAdaptor.FAIL_URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentErrorResponseDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookfront.payment.service.PaymentService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/paymentWidget")
    public String getPaymentWidget(@ModelAttribute OrderAddRequestDto orderAddRequestDto)
        throws JsonProcessingException {

        String orderId = orderAddRequestDto.getOrderId();
        String orderName = orderAddRequestDto.getOrderName();
        Long amount = orderAddRequestDto.getAmount();

        String widgetUrl = paymentService.getWidgetUrl(orderId, orderName, amount);

        return "redirect:" + widgetUrl;
    }


    @GetMapping(value = "/orders/success", params = {"paymentKey", "orderId", "amount"})
    public String successHandler(@RequestParam String paymentKey, @RequestParam String orderId,
                                 @RequestParam Long amount) {

        // requestPayment() 메서드에 담아 보낸 amount 값과 successUrl로 돌아온 amount 값이 같은지 확인해보기
        // 값이 다르면 결제 요청을 다시 하기

        PaymentApproveRequestDto
            requestDto = new PaymentApproveRequestDto(paymentKey, orderId, amount);
        PaymentResponseDto.PaymentDataResponseDto responseDto =
            paymentService.requestApproveApi(requestDto);
        if (Objects.isNull(responseDto)) {
            return "redirect:" + FAIL_URL;
        }

        return "redirect:/orders/completion/" + responseDto.getOrderId();
    }

    @GetMapping(value = "/orders/fail", params = {"code", "message", "orderId"})
    public String failureHandler(@RequestParam String code, @RequestParam String message,
                                 @RequestParam String orderId) {
        PaymentErrorResponseDto
            requestDto = new PaymentErrorResponseDto(code, message);

        return "redirect:/";
    }
}
