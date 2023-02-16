package shop.itbook.itbookfront.order.adaptor;

import java.net.URI;
import java.util.Objects;
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
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;

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

    private static final String PURCHASE_COMPLETE_API = "/api/orders/purchase-complete/";

    public <T> OrderSheetResponseDto findOrderSheet(URI uri,
                                                    HttpEntity<T> http) {
        ResponseEntity<CommonResponseBody<OrderSheetResponseDto>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET, http,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public PageResponse<OrderListMemberViewResponseDto> findOrderListViewResponseDtoPageResponse(
        URI uri) {

        ResponseEntity<CommonResponseBody<PageResponse<OrderListMemberViewResponseDto>>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public <T> OrderPaymentDto addOrder(URI uri, HttpEntity<T> http) {
        ResponseEntity<CommonResponseBody<OrderPaymentDto>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.POST, http,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public void postNullBodyReturnVoid(URI uri) {
        restTemplate.exchange(
            uri,
            HttpMethod.POST, null,
            new ParameterizedTypeReference<>() {
            });
    }

    public OrderDetailsResponseDto findOrderDetails(URI uri) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> http = new HttpEntity<>(headers);

        ResponseEntity<CommonResponseBody<OrderDetailsResponseDto>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET, http,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public PageResponse<OrderListAdminViewResponseDto> findOrderAdminListView(URI uri) {

        ResponseEntity<CommonResponseBody<PageResponse<OrderListAdminViewResponseDto>>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public void orderPurchaseComplete(Long orderNo) {

        restTemplate.exchange(
            PURCHASE_COMPLETE_API + orderNo,
            HttpMethod.POST,
            null,
            new ParameterizedTypeReference<>() {
            }
        );

    }
}
