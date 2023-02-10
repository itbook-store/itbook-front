package shop.itbook.itbookfront.payment.adaptor;

import java.util.Base64;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCreatedRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderNoResponseDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookfront.payment.exception.InvalidPaymentException;

/**
 * @author 이하늬
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAdaptor {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GatewayConfig gatewayConfig;

    private final String TOSS_REQUEST_URL = "https://api.tosspayments.com/v1/payments";
    private final String TEST_SECRET_KEY = "test_sk_mnRQoOaPz8L7Mlzvbmmry47BMw6v";

    private final String PAYMENT_METHOD = "카드";

    public OrderNoResponseDto requestApprovePayment(
        PaymentApproveRequestDto requestDto, Long orderNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentApproveRequestDto> httpEntity =
            new HttpEntity<>(requestDto, headers);
        ResponseEntity<CommonResponseBody<OrderNoResponseDto>> response =
            null;
        try {
            response =
                restTemplate.exchange(
                    gatewayConfig.getGatewayServer() + "/api/admin/payment/request-pay/" + orderNo,
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                    });

        } catch (BadRequestException e) {
            log.info(e.getMessage());
        }

        return Objects.requireNonNull(response.getBody()).getResult();
    }

//    public String requestWidget(String orderId, String orderName, Long amount) {
//        String encodedSecretKey =
//            Base64.getEncoder().encodeToString((TEST_SECRET_KEY + ":").getBytes());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBasicAuth(encodedSecretKey);
//
//        PaymentCreatedRequestDto paymentCreatedRequestDto
//            = PaymentCreatedRequestDto.builder()
//            .method(PAYMENT_METHOD)
//            .orderId(orderId)
//            .orderName(orderName)
//            .amount(amount)
//            .successUrl(SUCCESS_URL)
//            .failUrl(FAIL_URL)
//            .build();
//
//        HttpEntity<PaymentCreatedRequestDto> httpEntity =
//            new HttpEntity<>(paymentCreatedRequestDto, headers);
//
//        ResponseEntity<PaymentResponseDto.PaymentDataResponseDto> response = null;
//        try {
//            response = restTemplate.exchange(TOSS_REQUEST_URL, HttpMethod.POST, httpEntity,
//                PaymentResponseDto.PaymentDataResponseDto.class);
//
//        } catch (HttpClientErrorException e) {
//            log.info(e.getMessage());
//            throw new InvalidPaymentException();
//        }
//
//        return Objects.requireNonNull(response.getBody()).getCheckout().getUrl();
//    }

    // 취소한 주문에 대한 정보를 반환
    public OrderNoResponseDto requestCanceledPayment(
        PaymentCanceledRequestDto paymentCanceledRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentCanceledRequestDto> httpEntity =
            new HttpEntity<>(paymentCanceledRequestDto, headers);

        ResponseEntity<CommonResponseBody<OrderNoResponseDto>> response =
            null;
        try {
            response =
                restTemplate.exchange(
                    gatewayConfig.getGatewayServer() + "/api/admin/payment/request-cancel",
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                    });

        } catch (BadRequestException e) {
            log.info(e.getMessage());
        }

        return Objects.requireNonNull(response.getBody()).getResult();
    }
}
