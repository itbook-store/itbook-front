package shop.itbook.itbookfront.coupon.adaptor.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.coupon.dto.request.CouponInputRequestDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public Long addCoupon(CouponInputRequestDto couponInputRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CouponInputRequestDto> httpEntity = new HttpEntity<>(couponInputRequestDto, headers);

        ResponseEntity<CommonResponseBody<CouponNoResponseDto>> commonResponseBodyResponseEntity
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/coupon",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>(){

            });

        CommonResponseBody<CouponNoResponseDto> body = commonResponseBodyResponseEntity.getBody();
        CommonResponseBody.CommonHeader header = Objects.requireNonNull(body).getHeader();

        ResponseChecker.checkFail(commonResponseBodyResponseEntity.getStatusCode(),
            header.getResultMessage());

        return body.getResult().getCouponNo();
    }

    public List<CouponListResponseDto> findCouponList(String couponListUrl){

        ResponseEntity<CommonResponseBody<List<CouponListResponseDto>>> exchange =
        restTemplate.exchange(gatewayConfig.getGatewayServer() + couponListUrl,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        ResponseChecker.checkFail(exchange.getStatusCode(),
            Objects.requireNonNull(exchange.getBody().getHeader().getResultMessage()));

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }
}
