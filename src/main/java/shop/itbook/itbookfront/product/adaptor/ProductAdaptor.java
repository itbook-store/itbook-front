package shop.itbook.itbookfront.product.adaptor;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookfront.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.BookDetailsResponseDto;
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

    public ProductNoResponseDto addProduct(
        MultipartFile thumbnails, MultipartFile ebook, ProductBookRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("thumbnails", thumbnails.getResource());
        params.add("ebook", ebook.getResource());
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<ProductNoResponseDto>> response =
            restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products",
                HttpMethod.POST, uploadEntity, new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public List<BookDetailsResponseDto> findBookList(boolean isFiltered) {

        ResponseEntity<CommonResponseBody<List<BookDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + "/api/products/books?isFiltered=" + isFiltered,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public BookDetailsResponseDto findBook(Long productNo) {

        ResponseEntity<CommonResponseBody<BookDetailsResponseDto>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + "/api/admin/products/books/" + productNo,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
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

    public List<ProductDetailsResponseDto> findProductList(boolean isFiltered) {
        ResponseEntity<CommonResponseBody<List<ProductDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + "/api/products?isFiltered=" + isFiltered,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public void modifyProduct(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                              ProductBookRequestDto requestDto) {

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
        ResponseEntity<CommonResponseBody<ProductDetailsResponseDto>> response =
            restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products/" + productNo,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public List<CategoryDetailsResponseDto> findCategoryListWithProductNo(Long productNo) {
        ResponseEntity<CommonResponseBody<List<CategoryDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + "/api/products?productNo=" + productNo,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

    public List<ProductDetailsResponseDto> findProductListWithCategoryNo(Integer categoryNo) {
        ResponseEntity<CommonResponseBody<List<ProductDetailsResponseDto>>> response =
            restTemplate.exchange(
                gateway.getGatewayServer() + "/api/products?categoryNo=" + categoryNo,
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {

                });

        ResponseChecker.checkFail(response.getStatusCode(),
            Objects.requireNonNull(response.getBody()).getHeader().getResultMessage());

        return Objects.requireNonNull(response.getBody()).getResult();
    }

}
