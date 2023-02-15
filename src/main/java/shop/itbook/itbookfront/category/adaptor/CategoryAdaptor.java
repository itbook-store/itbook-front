package shop.itbook.itbookfront.category.adaptor;

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
import shop.itbook.itbookfront.category.exception.AlreadyAddedCategoryNameException;
import shop.itbook.itbookfront.category.exception.CategoryContainsProductsException;
import shop.itbook.itbookfront.category.exception.ParentCategoryNotFoundException;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.coupon.exception.CategoryNumberNotFoundException;
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

    public PageResponse<CategoryListResponseDto> findCategoryList(String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<CommonResponseBody<PageResponse<CategoryListResponseDto>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + url,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponseBody<PageResponse<CategoryListResponseDto>> body = exchange.getBody();
        assert body != null;
        return body.getResult();
    }

    public Integer addCategory(CategoryRequestDto categoryRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryRequestDto> httpEntity = new HttpEntity<>(categoryRequestDto, headers);
        ResponseEntity<CommonResponseBody<CategoryNoResponseDto>> commonResponseBodyResponseEntity =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories",
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody<CategoryNoResponseDto> body = commonResponseBodyResponseEntity.getBody();
        assert body != null;
        return body.getResult().getCategoryNo();
    }

    public void modifyCategoryHidden(String categoryNo) {

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo +
                "/hidden",
            HttpMethod.PUT,
            null, new ParameterizedTypeReference<>() {
            });
    }

    public void modifyMainCategorySequence(Integer categoryNo, Integer sequence) {

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo +
                "/main-sequence?sequence=" + sequence,
            HttpMethod.PUT,
            null, new ParameterizedTypeReference<>() {
            });
    }

    public void modifySubCategorySequence(Integer categoryNo, Integer hopingPositionCategoryNo) {

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo +
                "/child-sequence?hopingPositionCategoryNo=" + hopingPositionCategoryNo,
            HttpMethod.PUT,
            null, new ParameterizedTypeReference<>() {
            });
    }

    public void modifyCategory(Integer categoryNo, CategoryModifyRequestDto categoryRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryModifyRequestDto> entity = new HttpEntity(categoryRequestDto, headers);
        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo,
            HttpMethod.PUT,
            entity, new ParameterizedTypeReference<>() {
            });

    }

    public void deleteCategory(String categoryNo) {

        restTemplate.exchange(
            gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo,
            HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
            });
    }
}
