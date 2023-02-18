package shop.itbook.itbookfront.coupon.adaptor.serviceapi.impl;

import java.util.List;
import java.util.Map;
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
import shop.itbook.itbookfront.coupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookfront.coupon.dto.response.UserCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MembershipCouponAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public Map<String, List<MembershipCouponResponseDto>> getUserAllCouponIssueList(
        String membershipCouponListUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<CommonResponseBody<Map<String, List<MembershipCouponResponseDto>>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + membershipCouponListUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

}
