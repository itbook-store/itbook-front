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
        ResponseEntity<CommonResponseBody<CategoryNoResponseDto>> commonResponseBodyResponseEntity = null;
        try {
            commonResponseBodyResponseEntity =
                restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories",
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                    });
        } catch (BadRequestException e) {
            if (Objects.equals(e.getMessage(), AlreadyAddedCategoryNameException.MESSAGE)) {
                throw new AlreadyAddedCategoryNameException();
            }

            if (Objects.equals(e.getMessage(), ParentCategoryNotFoundException.MESSAGE)) {
                throw new ParentCategoryNotFoundException();
            }
        }

        CommonResponseBody<CategoryNoResponseDto> body = commonResponseBodyResponseEntity.getBody();
        return body.getResult().getCategoryNo();
    }

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

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public void deleteCategory(String categoryNo) {

        try {
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo,
                HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                });
        } catch (BadRequestException e) {
            if (Objects.equals(e.getMessage(), CategoryContainsProductsException.MESSAGE)) {
                throw new CategoryContainsProductsException();
            }
        }
    }

    public void modifyCategoryHidden(String categoryNo) {

        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/categories/" + categoryNo + "/hidden",
                HttpMethod.PUT,
                null, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody.CommonHeader header = Objects.requireNonNull(exchange.getBody()).getHeader();
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
