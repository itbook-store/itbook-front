package shop.itbook.itbookfront.product.adaptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookfront.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookfront.product.dto.response.GetBookListResponseDto;
import shop.itbook.itbookfront.product.exception.BadRequestException;
import shop.itbook.itbookfront.product.exception.MemberForbiddenException;
import shop.itbook.itbookfront.product.exception.RestApiServerException;

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

    public AddProductResponseDto addBook(
        AddProductBookRequestDto requestDto) {

        URI uri = UriComponentsBuilder.fromHttpUrl(
                gateway.getGatewayServer() + "/api/admin/products/books")
            .build().toUri();

        CommonResponseBody<AddProductResponseDto> response =
            restTemplate.postForObject(uri, requestDto,
                CommonResponseBody.class);

        CommonResponseBody.CommonHeader header = response.getHeader();
        if (!header.isSuccessful()) {
            if (header.getResultCode().equals(400)) {
                new BadRequestException(header.getResultMessage());
                throw new BadRequestException(header.getResultMessage());
            }

            if (header.getResultCode().equals(403)) {
                throw new MemberForbiddenException(header.getResultMessage());
            }

            if (header.getResultCode().equals(500)) {
                throw new RestApiServerException(header.getResultMessage());
            }
        }
        return objectMapper.convertValue(response.getResult(), AddProductResponseDto.class);

    }

    public List<GetBookListResponseDto> getBookList() {

        URI uri = UriComponentsBuilder.fromHttpUrl(
                gateway.getGatewayServer() + "/api/admin/products/books")
            .build().toUri();

        CommonResponseBody<List<GetBookListResponseDto>> response =
            restTemplate.getForObject(uri, CommonResponseBody.class);

        CommonResponseBody.CommonHeader header = response.getHeader();
        if (!header.isSuccessful()) {
            if (header.getResultCode().equals(400)) {
                new BadRequestException(header.getResultMessage());
                throw new BadRequestException(header.getResultMessage());
            }

            if (header.getResultCode().equals(403)) {
                throw new MemberForbiddenException(header.getResultMessage());
            }

            if (header.getResultCode().equals(500)) {
                throw new RestApiServerException(header.getResultMessage());
            }
        }
        return objectMapper.convertValue(response.getResult(),
            new TypeReference<List<GetBookListResponseDto>>() {
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
