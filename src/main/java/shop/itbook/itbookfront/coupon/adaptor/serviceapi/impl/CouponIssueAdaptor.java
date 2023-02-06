package shop.itbook.itbookfront.coupon.adaptor.serviceapi.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponIssueAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PageResponse<UserCouponIssueListResponseDto> getUserAllCouponIssueList(String userAllCouponIssueListUrl){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity< CommonResponseBody <PageResponse<UserCouponIssueListResponseDto>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + userAllCouponIssueListUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public void usePointCoupon(String usePointCouponIssueUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + usePointCouponIssueUrl,
            HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
            });
    }

    public Long addCouponByCouponType(String couponIssueByCouponTypeUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<CouponNoResponseDto>> exchange
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + couponIssueByCouponTypeUrl,
            HttpMethod.POST, null, new ParameterizedTypeReference<>(){

            });

        return Objects.requireNonNull(exchange.getBody()).getResult().getCouponNo();
    }

    public List<CouponListResponseDto> getCouponsByCouponType(String couponsByCouponTypeUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<List<CouponListResponseDto>>> exchange
            = restTemplate.exchange(gatewayConfig.getGatewayServer() + couponsByCouponTypeUrl,
            HttpMethod.GET, null, new ParameterizedTypeReference<>(){

            });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }
}
