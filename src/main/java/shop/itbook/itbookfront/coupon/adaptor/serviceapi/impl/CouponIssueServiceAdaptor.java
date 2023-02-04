package shop.itbook.itbookfront.coupon.adaptor.serviceapi.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CouponIssueServiceAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PageResponse<UserCouponIssueListResponseDto> getUserAllCouponIssueList(String userAllCouponIssueListUrl){
        ResponseEntity< CommonResponseBody <PageResponse<UserCouponIssueListResponseDto>>> exchage =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + userAllCouponIssueListUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(exchage.getStatusCode(),
            Objects.requireNonNull(exchage.getBody().getHeader().getResultMessage()));

        return exchage.getBody().getResult();
    }
}
