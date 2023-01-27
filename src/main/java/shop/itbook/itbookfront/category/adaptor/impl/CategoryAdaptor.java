package shop.itbook.itbookfront.category.adaptor.impl;

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
import shop.itbook.itbookfront.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookfront.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookfront.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.util.ResponseChecker;
import shop.itbook.itbookfront.category.dto.response.CategoryListResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class CategoryAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public Integer addCategory(CategoryRequestDto categoryRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryRequestDto> httpEntity = new HttpEntity<>(categoryRequestDto, headers);

        ResponseEntity<CommonResponseBody<CategoryNoResponseDto>> commonResponseBodyResponseEntity =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories",
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody<CategoryNoResponseDto> body = commonResponseBodyResponseEntity.getBody();
        CommonResponseBody.CommonHeader header = Objects.requireNonNull(body).getHeader();

        ResponseChecker.checkFail(commonResponseBodyResponseEntity.getStatusCode(),
            header.getResultMessage());

        return body.getResult().getCategoryNo();
    }

    public List<CategoryListResponseDto> findCategoryList(String url) {

        ResponseEntity<CommonResponseBody<List<CategoryListResponseDto>>> exchange =
        restTemplate.exchange(gatewayConfig.getGatewayServer() + url,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        ResponseChecker.checkFail(exchange.getStatusCode(),
            Objects.requireNonNull(exchange.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public void deleteCategory(String categoryNo) {

        ResponseEntity<CommonResponseBody<Void>> exchange = restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo,
            HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
            });


        CommonResponseBody.CommonHeader header =
            Objects.requireNonNull(exchange.getBody()).getHeader();
        ResponseChecker.checkFail(exchange.getStatusCode(), header.getResultMessage());
    }

    public void modifyCategoryHidden(String categoryNo) {

        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo +
                    "/hidden",
                HttpMethod.PUT,
                null, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody.CommonHeader header =
            Objects.requireNonNull(exchange.getBody()).getHeader();
        ResponseChecker.checkFail(exchange.getStatusCode(), header.getResultMessage());
    }

    public void modifyMainCategorySequence(Integer categoryNo, Integer sequence) {
        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo + "/main-sequence?sequence=" + sequence,
                HttpMethod.PUT,
                null, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody.CommonHeader header = Objects.requireNonNull(exchange.getBody()).getHeader();
        ResponseChecker.checkFail(exchange.getStatusCode(), header.getResultMessage());
    }

    public void modifySubCategorySequence(Integer categoryNo, Integer hopingPositionCategoryNo) {
        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo + "/child-sequence?hopingPositionCategoryNo=" + hopingPositionCategoryNo,
                HttpMethod.PUT,
                null, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody.CommonHeader header = Objects.requireNonNull(exchange.getBody()).getHeader();
        ResponseChecker.checkFail(exchange.getStatusCode(), header.getResultMessage());
    }

    public void modifyCategory(Integer categoryNo, CategoryModifyRequestDto categoryRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryModifyRequestDto> entity = new HttpEntity(categoryRequestDto, headers);
        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo,
                HttpMethod.PUT,
                entity, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody.CommonHeader header = Objects.requireNonNull(exchange.getBody()).getHeader();
        ResponseChecker.checkFail(exchange.getStatusCode(), header.getResultMessage());
    }
}
