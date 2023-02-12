package shop.itbook.itbookfront.productinquiry.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductInquiryAdaptor {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public PageResponse<ProductInquiryResponseDto> findProductInquiryList(String url) {

        ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryResponseDto>>> exchange =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/product-inquiries" + url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody().getResult();
    }

    public Long addProductInquiry(ProductInquiryRequestDto requestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> uploadEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<CommonResponseBody<ProductInquiryNoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/product-inquiries/add", HttpMethod.POST,
                uploadEntity,
                new ParameterizedTypeReference<>() {
                });

        CommonResponseBody<ProductInquiryNoResponseDto> body = responseEntity.getBody();

        return body.getResult().getProductInquiryNo();
    }
}
