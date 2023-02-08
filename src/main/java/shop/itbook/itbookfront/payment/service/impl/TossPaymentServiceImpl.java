package shop.itbook.itbookfront.payment.service.impl;

import java.util.Base64;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.exception.RestApiServerException;
import shop.itbook.itbookfront.payment.adaptor.PaymentAdaptor;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCreatedRequestDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookfront.payment.exception.InvalidPaymentException;
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
    public PaymentResponseDto.PaymentDataResponseDto requestApproveApi(
        PaymentApproveRequestDto requestDto) {
        PaymentResponseDto.PaymentDataResponseDto responseDto =
            paymentAdaptor.requestApproveApi(requestDto);
        
        return responseDto;
    }
}
