package shop.itbook.itbookfront.order.adaptor;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.order.dto.request.OrderProductRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaperResponseDto;

/**
 * shop 서버와 주문 관련 정보를 통신하기 위한 클래스
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class OrderAdaptor {
    private final RestTemplate restTemplate;

    public List<OrderPaperResponseDto> findOrderProductList(URI uri,
                                                            HttpEntity<OrderProductRequestDto> http) {
        ResponseEntity<CommonResponseBody<List<OrderPaperResponseDto>>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET, http,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

}
