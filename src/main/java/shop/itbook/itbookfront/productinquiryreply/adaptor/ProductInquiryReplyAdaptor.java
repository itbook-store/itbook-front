package shop.itbook.itbookfront.productinquiryreply.adaptor;

import java.util.List;
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
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookfront.productinquiryreply.dto.response.ProductInquiryReplyNoResponseDto;
import shop.itbook.itbookfront.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductInquiryReplyAdaptor {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    public Integer addProductInquiryReply(ProductInquiryReplyRequestDto requestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> uploadEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<CommonResponseBody<ProductInquiryReplyNoResponseDto>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/admin/product-inquiries/reply/add",
                HttpMethod.POST, uploadEntity,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult().getProductInquiryReplyNo();
    }

    public List<ProductInquiryReplyResponseDto> findProductInquiryReplyList(Long productInquiryNo) {

        ResponseEntity<CommonResponseBody<List<ProductInquiryReplyResponseDto>>> responseEntity =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + "/api/product-inquiries/reply/view/" +
                    productInquiryNo, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return responseEntity.getBody().getResult();
    }
}
