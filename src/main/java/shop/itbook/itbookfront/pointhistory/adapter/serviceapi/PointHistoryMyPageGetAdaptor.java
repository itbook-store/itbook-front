package shop.itbook.itbookfront.pointhistory.adapter.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.pointhistory.dto.response.PointHistoryListDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PointHistoryMyPageGetAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PageResponse<PointHistoryListDto> findMyPointHistoryList(String apiUrl) {

        ResponseEntity<CommonResponseBody<PageResponse<PointHistoryListDto>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        return exchange.getBody().getResult();
    }
}
