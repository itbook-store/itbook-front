package shop.itbook.itbookfront.coupon.adaptor.adminapi.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.AdminCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponIssueAdminAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private static final String BASE_API_URL = "/api/admin/coupon-issues";

    public PageResponse<AdminCouponIssueListResponseDto> findCouponIssueList(String couponIssueListUrl){

        ResponseEntity<CommonResponseBody<PageResponse<AdminCouponIssueListResponseDto>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + BASE_API_URL+
                couponIssueListUrl,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }
}
