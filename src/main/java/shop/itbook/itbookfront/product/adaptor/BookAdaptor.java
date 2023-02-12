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
import shop.itbook.itbookfront.common.exception.BadRequestException;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.request.BookAddRequestDto;
import shop.itbook.itbookfront.product.dto.request.BookModifyRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductBooleanResponseDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.SearchBookDetailsDto;
import shop.itbook.itbookfront.product.exception.InvalidInputException;
import shop.itbook.itbookfront.util.ResponseChecker;

/**
 * @author 이하늬
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class BookAdaptor {
    private final GatewayConfig gateway;
    private final RestTemplate restTemplate;

    public void modifyBook(Long productNo, MultipartFile thumbnails, MultipartFile ebook,
                           BookModifyRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        if (Objects.nonNull(thumbnails)) {
            params.add("thumbnails", thumbnails.getResource());
        }
        if (Objects.nonNull(ebook)) {
            params.add("ebook", ebook.getResource());
        }
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        ResponseEntity<CommonResponseBody<Void>> response = restTemplate.exchange(
            gateway.getGatewayServer() + "/api/admin/products/books/" + productNo,
            HttpMethod.PUT, uploadEntity, new ParameterizedTypeReference<>() {
            });
    }

    public Long addBook(
        MultipartFile thumbnails, MultipartFile ebook, BookAddRequestDto requestDto) {

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
        return Objects.requireNonNull(response.getBody()).getResult().getProductNo();

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
}
