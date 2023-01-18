package shop.itbook.itbookfront.category.adaptor.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.util.ResponseChecker;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CategoryAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public CommonResponseBody<shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto> addCategory(
        CategoryRequestDto categoryRequestDto) {
        return restTemplate.postForEntity(gatewayConfig.getGatewayServer() + "/categories",
            categoryRequestDto, CommonResponseBody.class).getBody();
    }

    public List<CategoryListResponseDto> findCategoryList(String url) {

        ResponseEntity<CommonResponseBody<List<CategoryListResponseDto>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

//        ResponseChecker.checkFail(exchange.getStatusCode(), exchange.getBody().getHeader());

        return exchange.getBody().getResult();
    }


}
