package shop.itbook.itbookfront.product.adaptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */

@Component
@RequiredArgsConstructor
public class ProductAdaptor {
    private final GatewayConfig gateway;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ProductNoResponseDto addBook(
        MultipartFile thumbnails, MultipartFile ebook, AddProductBookRequestDto requestDto) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("thumbnails", thumbnails.getResource());
        params.add("ebook", ebook.getResource());
        params.add("requestDto", requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> uploadEntity = new HttpEntity<>(params, headers);

        CommonResponseBody<ProductNoResponseDto> response =
            restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products/books",
                HttpMethod.POST, uploadEntity,
                new ParameterizedTypeReference<CommonResponseBody<ProductNoResponseDto>>() {
                }).getBody();

        return objectMapper.convertValue(response.getResult(), ProductNoResponseDto.class);
    }

    public List<GetBookResponseDto> getBookList() {

        ResponseEntity<CommonResponseBody<List<GetBookResponseDto>>> response =
            restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products/books",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return objectMapper.convertValue(response.getBody().getResult(),
            new TypeReference<>() {
            });


    }

    public GetBookResponseDto getBook(Long id) {

        ResponseEntity<CommonResponseBody<GetBookResponseDto>> response =
            restTemplate.exchange(gateway.getGatewayServer() + "/api/admin/products/books/" + id,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return objectMapper.convertValue(response.getBody().getResult(),
            new TypeReference<>() {
            });


    }

//    public ResponseEntity<CommonResponseBody<AddProductResponseDto>> addBook(
//        AddProductBookRequestDto requestDto, MultipartFile thumbnails, MultipartFile ebook) {
//
//        requestDto.setFileThumbnailsName(thumbnails.getOriginalFilename());
//        if (!Objects.isNull(ebook)) {
//            requestDto.setFileEbookName(ebook.getOriginalFilename());
//        }
//        URI uri = UriComponentsBuilder.fromHttpUrl(
//                gateway.getGatewayServer() + "/api/admin/products")
//            .build().toUri();
//
//        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(requestDto),
//            new ParameterizedTypeReference<>() {
//            });
//    }
}
