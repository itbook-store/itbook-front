package shop.itbook.itbookfront.pointhistory.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookfront.pointhistory.dto.response.details.PointHistoryDetailsGiftResponseDto;
import shop.itbook.itbookfront.pointhistory.dto.response.details.PointHistoryGradeDetailsResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PointHistoryDetailsGetAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PointHistoryDetailsGiftResponseDto findPointHistoryDetailsGift(String apiUrl) {

        ResponseEntity<CommonResponseBody<PointHistoryDetailsGiftResponseDto>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody().getResult();
    }

    public PointHistoryGradeDetailsResponseDto findPointHistoryDetailsGrade(String apiUrl) {

        ResponseEntity<CommonResponseBody<PointHistoryGradeDetailsResponseDto>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody().getResult();
    }
}
