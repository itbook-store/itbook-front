package shop.itbook.itbookfront.elastic.adaptor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.elastic.dto.response.ProductSampleResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductSearchAdaptor {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PageResponse<ProductSampleResponseDto> findProductList(String url) {

        ResponseEntity<CommonResponseBody<PageResponse<ProductSampleResponseDto>>>
            exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody().getResult();
    }
}
