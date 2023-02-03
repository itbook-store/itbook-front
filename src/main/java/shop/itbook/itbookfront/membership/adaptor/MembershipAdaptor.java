package shop.itbook.itbookfront.membership.adaptor;

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
import shop.itbook.itbookfront.membership.dto.response.MembershipHistoryResponseDto;
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MembershipAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public List<MembershipResponseDto> getMemberships() {
        ResponseEntity<CommonResponseBody<List<MembershipResponseDto>>> responseEntity =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/membership",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(responseEntity.getStatusCode(),
            responseEntity.getBody().getHeader().getResultMessage());

        return responseEntity.getBody().getResult();
    }

    public List<MembershipHistoryResponseDto> getMembershipHistoriesByMemberId(String memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<List<MembershipHistoryResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/membership-history/" + memberId,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(responseEntity.getBody()).getResult();
    }
}
