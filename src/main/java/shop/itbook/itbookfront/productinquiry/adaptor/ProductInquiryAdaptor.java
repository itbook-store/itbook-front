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
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookfront.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
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

        ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryResponseDto>>> responseEntity =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/admin/product-inquiries" + url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
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

        return responseEntity.getBody().getResult().getProductInquiryNo();
    }

    public ProductInquiryCountResponseDto countProductInquiry() {
        ResponseEntity<CommonResponseBody<ProductInquiryCountResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/product-inquiries/count",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public PageResponse<ProductInquiryOrderProductResponseDto> findProductInquiryOrderProductList(String url, Long memberNo) {

        ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryOrderProductResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/product-inquiries/writable/" + memberNo +
                    url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public ProductInquiryResponseDto findProductInquiry(Long productInquiryNo) {
        ResponseEntity<CommonResponseBody<ProductInquiryResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/product-inquiries/view/" +
                    productInquiryNo,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public ProductInquiryResponseDto findProductInquiryInProductDetails(Long memberNo, Long productInquiryNo) {

        ResponseEntity<CommonResponseBody<ProductInquiryResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/product-inquiries/view/" + memberNo + "/" +
                    productInquiryNo,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public PageResponse<ProductInquiryResponseDto> findProductInquiryListByMemberNo(String url, Long memberNo) {

        ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryResponseDto>>> responseEntity =
            restTemplate.exchange(gatewayConfig.getGatewayServer() + "/api/product-inquiries/list/" + memberNo + url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }

    public PageResponse<ProductInquiryResponseDto> findProductInquiryListByProductNo(String url, Long productNo) {

        ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/product-inquiries/product/list/" +
                    productNo + url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }
}
