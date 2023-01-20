package shop.itbook.itbookfront.delivery.adminapi.adaptor;

import java.net.URI;
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
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryDetailResponseDto;
import shop.itbook.itbookfront.delivery.adminapi.dto.response.DeliveryWithStatusResponseDto;

/**
 * The type Delivery adaptor.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class DeliveryAdaptor {

    private final RestTemplate restTemplate;

    /**
     * 상태가 배송 대기중인 배송 정보의 리스트를 받아옵니다.
     *
     * @param uri 게이트웨이 uri
     * @return 배송 상태가 배송 대기중인 배송 정보 리스트
     */
    public ResponseEntity<CommonResponseBody<List<DeliveryWithStatusResponseDto>>> getDeliveryWaitList(
        URI uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });
    }

    /**
     * 배송 상태가 배송 대기중인 상품들의 배송 등록을 요청합~니다.
     *
     * @param uri 게이트웨이 uri
     * @return 등록 성공
     */
    public ResponseEntity<CommonResponseBody<List<DeliveryDetailResponseDto>>> postDeliveryList(
        URI uri) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(null, headers),
            new ParameterizedTypeReference<>() {
            });
    }
}
