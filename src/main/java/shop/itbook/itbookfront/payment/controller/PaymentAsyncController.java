package shop.itbook.itbookfront.payment.controller;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
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

    @PostMapping(value = "async/payment/cancel", params = "isSubscription")
    public OrderResponseDto requestCancelPayment(@RequestParam boolean isSubscription,
                                                 @RequestBody
                                                 PaymentCanceledRequestDto paymentCanceledRequestDto,
                                                 @AuthenticationPrincipal
                                                 UserDetailsDto userDetailsDto) {
        boolean isMemberOrder = false;
        if (Optional.ofNullable(userDetailsDto).isPresent()) {
            isMemberOrder = true;
        }

        OrderResponseDto orderResponseDto;
        try {
            orderResponseDto =
                paymentService.requestCanceledPayment(paymentCanceledRequestDto, isMemberOrder,
                    isSubscription);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            throw e;
        }
        return orderResponseDto;
    }
}
