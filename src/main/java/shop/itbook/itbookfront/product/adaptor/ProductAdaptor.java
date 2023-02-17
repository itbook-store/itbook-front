package shop.itbook.itbookfront.product.adaptor;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.exception.InvalidInputException;
import shop.itbook.itbookfront.product.exception.ProductNotFoundException;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 이하늬
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class ProductAdaptor {
    private final GatewayConfig gateway;
    private final RestTemplate restTemplate;

    public Long addProduct(
        MultipartFile thumbnails, ProductAddRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("thumbnails", thumbnails.getResource());
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<ProductNoResponseDto>> response =
            restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products",
                HttpMethod.POST, uploadEntity, new ParameterizedTypeReference<>() {
                });


        return Objects.requireNonNull(response.getBody()).getResult().getProductNo();

    }

    public PageResponse<ProductDetailsResponseDto> findProductList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        return Objects.requireNonNull(response.getBody()).getResult();

    }

    public void modifyProduct(Long productNo, MultipartFile thumbnails,
                              ProductModifyRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        if (Objects.nonNull(thumbnails)) {
            params.add("thumbnails", thumbnails.getResource());
        }
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        restTemplate.exchange(
            gateway.getGatewayServer() + "/api/admin/products/" + productNo,
            HttpMethod.PUT, uploadEntity, new ParameterizedTypeReference<>() {
            });

    }


    public ProductDetailsResponseDto findProduct(Long productNo) {
        ResponseEntity<CommonResponseBody<ProductDetailsResponseDto>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + "/api/admin/products/" + productNo,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });


        return Objects.requireNonNull(response.getBody()).getResult();

    }

    public PageResponse<CategoryDetailsResponseDto> findCategoryList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<CategoryDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        return Objects.requireNonNull(response.getBody()).getResult();

    }

    public PageResponse<ProductTypeResponseDto> findProductTypeList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductTypeResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        return Objects.requireNonNull(response.getBody()).getResult();

    }

    public PageResponse<ProductRelationResponseDto> findRelationProductList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductRelationResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        return Objects.requireNonNull(response.getBody()).getResult();

    }

    public void modifyRelationProduct(Long basedProductNo, ProductRelationRequestDto requestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductRelationRequestDto> entity = new HttpEntity(requestDto, headers);
        restTemplate.exchange(
            gateway.getGatewayServer() +
                String.format("/api/products/relation/%d/edit", basedProductNo),
            HttpMethod.POST,
            entity, new ParameterizedTypeReference<>() {
            });

    }

    public void changeBooleanField(Long productNo, String fieldName) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gateway.getGatewayServer())
            .path(String.format("/api/admin/products/modify/%d", productNo))
            .queryParam("fieldName", fieldName)
            .build();

        restTemplate.exchange(
            uriComponents.toUriString(), HttpMethod.PUT, null,
            new ParameterizedTypeReference<>() {
            });
    }

    public void changeDailyHits(Long productNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gateway.getGatewayServer())
            .path(String.format("/api/admin/products/modify-dailyhits/%d", productNo))
            .build();

        restTemplate.exchange(
            uriComponents.toUriString(), HttpMethod.PUT, null,
            new ParameterizedTypeReference<>() {
            });

    }

    public PageResponse<ProductSalesRankResponseDto> findSalesRankProductList(
        @PageableDefault Pageable pageable, String sortingCriteria) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductSalesRankResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer()
                    + String.format(
                    "/api/admin/products/sales-rank?sortingCriteria=%s&page=%d&size=%d",
                    sortingCriteria, pageable.getPageNumber(), pageable.getPageSize()),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        return Objects.requireNonNull(response.getBody()).getResult();
    }
}
