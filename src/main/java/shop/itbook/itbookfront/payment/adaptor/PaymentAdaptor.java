package shop.itbook.itbookfront.payment.adaptor;

import java.util.Base64;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCreatedRequestDto;
import shop.itbook.itbookfront.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookfront.payment.exception.InvalidPaymentException;

/**
 * @author 이하늬
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PaymentAdaptor {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GatewayConfig gatewayConfig;

    private final String TOSS_REQUEST_URL = "https://api.tosspayments.com/v1/payments";
    private final String TEST_SECRET_KEY = "test_sk_mnRQoOaPz8L7Mlzvbmmry47BMw6v";

    private final String PAYMENT_METHOD = "카드";
    @Value("${payment.success.url}")
    private String SUCCESS_URL = "http://localhost:8080/orders/success";
    @Value("${payment.fail.url}")
    private String FAIL_URL = "http://localhost:8080/orders/fail";

    public PaymentResponseDto.PaymentDataResponseDto requestApproveApi(
        PaymentApproveRequestDto requestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentApproveRequestDto> httpEntity =
            new HttpEntity<>(requestDto, headers);
        ResponseEntity<CommonResponseBody<PaymentResponseDto.PaymentDataResponseDto>> response =
            null;
        try {
            response =
                restTemplate.exchange(
                    gatewayConfig.getGatewayServer() + "/api/admin/payment/request-pay",
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                    });
        } catch (BadRequestException e) {
            if (Objects.equals(e.getMessage(), InvalidPaymentException.MESSAGE)) {
                throw new InvalidPaymentException();
            }
        }

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public String requestWidget(String orderId, String orderName, Long amount) {
        String encodedSecretKey =
            Base64.getEncoder().encodeToString((TEST_SECRET_KEY + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(encodedSecretKey);

        PaymentCreatedRequestDto paymentCreatedRequestDto
            = PaymentCreatedRequestDto.builder()
            .method(PAYMENT_METHOD)
            .orderId(orderId)
            .orderName(orderName)
            .amount(amount)
            .successUrl(SUCCESS_URL)
            .failUrl(FAIL_URL)
            .build();

        HttpEntity<PaymentCreatedRequestDto> httpEntity =
            new HttpEntity<>(paymentCreatedRequestDto, headers);

        ResponseEntity<PaymentResponseDto.PaymentDataResponseDto> response =
            restTemplate.exchange(TOSS_REQUEST_URL, HttpMethod.POST, httpEntity,
                PaymentResponseDto.PaymentDataResponseDto.class);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new InvalidPaymentException();
        }

        String checkOutUrl = response.getBody().getCheckout().getUrl();

        return checkOutUrl;
    }
}
