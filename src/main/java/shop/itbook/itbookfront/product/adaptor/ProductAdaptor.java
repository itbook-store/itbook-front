package shop.itbook.itbookfront.product.adaptor;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
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
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookfront.category.exception.AlreadyAddedCategoryNameException;
import shop.itbook.itbookfront.category.exception.CategoryContainsProductsException;
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.request.BookRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookfront.product.dto.request.ProductRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductRelationResponseDto;
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
        MultipartFile thumbnails, ProductRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("thumbnails", thumbnails.getResource());
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<ProductNoResponseDto>> response = null;

        try {
            response =
                restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products",
                    HttpMethod.POST, uploadEntity, new ParameterizedTypeReference<>() {
                    });
        } catch (BadRequestException e) {
            if (Objects.equals(e.getMessage(), InvalidInputException.MESSAGE)) {
                throw new InvalidInputException();
            }
        }
        CommonResponseBody<ProductNoResponseDto> body = response.getBody();
        return body.getResult().getProductNo();
    }

    public Long addBook(
        MultipartFile thumbnails, MultipartFile ebook, BookRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("thumbnails", thumbnails.getResource());
        params.add("ebook", ebook.getResource());
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<ProductNoResponseDto>> response = null;
        try {
            response =
                restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products/books",
                    HttpMethod.POST, uploadEntity, new ParameterizedTypeReference<>() {
                    });
        } catch (BadRequestException e) {
            if (Objects.equals(e.getMessage(), InvalidInputException.MESSAGE)) {
                throw new InvalidInputException();
            }
        }
        CommonResponseBody<ProductNoResponseDto> body = response.getBody();
        return body.getResult().getProductNo();
    }

    public void removeProduct(Long productNo) {
        ResponseEntity<CommonResponseBody<Void>> response = restTemplate.exchange(
            gateway.getGatewayServer() + "/api/admin/products/" + productNo,
            HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
            });

        CommonResponseBody.CommonHeader header =
            Objects.requireNonNull(response.getBody()).getHeader();
        ResponseChecker.checkFail(response.getStatusCode(), header.getResultMessage());
    }

    public PageResponse<ProductDetailsResponseDto> findProductList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                              BookRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("thumbnails", thumbnails.getResource());
        params.add("ebook", ebook.getResource());
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<Void>> response = restTemplate.exchange(
            gateway.getGatewayServer() + "/api/admin/products/" + productNo,
            HttpMethod.PUT, uploadEntity, new ParameterizedTypeReference<>() {
            });

        CommonResponseBody.CommonHeader header =
            Objects.requireNonNull(response.getBody()).getHeader();
        ResponseChecker.checkFail(response.getStatusCode(), header.getResultMessage());
    }

    public ProductDetailsResponseDto findProduct(Long productNo) {
        ResponseEntity<CommonResponseBody<ProductDetailsResponseDto>> response = null;
        try {
            response = restTemplate.exchange(
                gateway.getGatewayServer() + "/api/admin/products/" + productNo,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        } catch (
            BadRequestException e) {
            if (Objects.equals(e.getMessage(), ProductNotFoundException.MESSAGE)) {
                throw new ProductNotFoundException();
            }
        }

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public PageResponse<CategoryDetailsResponseDto> findCategoryList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<CategoryDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public PageResponse<ProductTypeResponseDto> findProductTypeList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductTypeResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public PageResponse<ProductRelationResponseDto> findRelationProductList(String url) {
        ResponseEntity<CommonResponseBody<PageResponse<ProductRelationResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public SearchBookDetailsDto searchBook(String url) {
        ResponseEntity<CommonResponseBody<SearchBookDetailsDto>> response =
            restTemplate.exchange(gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public ProductBooleanResponseDto isbnExists(String url) {
        ResponseEntity<CommonResponseBody<ProductBooleanResponseDto>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + url,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public void modifyRelationProduct(Long basedProductNo, ProductRelationRequestDto requestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductRelationRequestDto> entity = new HttpEntity(requestDto, headers);
        ResponseEntity<CommonResponseBody<Void>> exchange =
            restTemplate.exchange(
                gateway.getGatewayServer() +
                    String.format("/api/products/relation/%d/edit", basedProductNo),
                HttpMethod.POST,
                entity, new ParameterizedTypeReference<>() {
                });

        CommonResponseBody.CommonHeader header =
            Objects.requireNonNull(exchange.getBody()).getHeader();
        ResponseChecker.checkFail(exchange.getStatusCode(), header.getResultMessage());
    }
}
