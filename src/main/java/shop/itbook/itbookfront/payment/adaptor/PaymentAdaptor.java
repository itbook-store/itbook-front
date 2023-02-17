package shop.itbook.itbookfront.payment.adaptor;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookfront.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookfront.payment.dto.response.OrderResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public OrderResponseDto requestApprovePayment(
        PaymentApproveRequestDto requestDto, Long orderNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentApproveRequestDto> httpEntity =
            new HttpEntity<>(requestDto, headers);
        ResponseEntity<CommonResponseBody<OrderResponseDto>> response =
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

    // 취소한 주문에 대한 정보를 반환
    public OrderResponseDto requestCanceledPayment(
        PaymentCanceledRequestDto paymentCanceledRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentCanceledRequestDto> httpEntity =
            new HttpEntity<>(paymentCanceledRequestDto, headers);

        ResponseEntity<CommonResponseBody<OrderResponseDto>> response =
            null;

        response =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/payment/request-cancel",
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });


        return Objects.requireNonNull(response.getBody()).getResult();
    }
}
