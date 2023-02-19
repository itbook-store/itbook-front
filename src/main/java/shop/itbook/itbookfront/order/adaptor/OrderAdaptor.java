package shop.itbook.itbookfront.order.adaptor;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.itbook.itbookfront.common.response.CommonResponseBody;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionListDto;

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

    private final GatewayConfig gatewayConfig;

    private static final String PURCHASE_COMPLETE_API = "/api/orders/purchase-complete/";

    private static final String ORDER_SUBSCRIPTION_ADMIN_LIST_API =
        "/api/admin/orders/list/subscription";

    private static final String ORDER_SUBSCRIPTION_MEMBER_LIST_API =
        "/api/orders/list/subscription/";

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
            gatewayConfig.getGatewayServer() + PURCHASE_COMPLETE_API + orderNo,
            HttpMethod.POST,
            null,
            new ParameterizedTypeReference<>() {
            }
        );
    }

    public PageResponse<OrderSubscriptionAdminListDto> orderSubscriptionListByAdmin(URI uri) {

        ResponseEntity<CommonResponseBody<PageResponse<OrderSubscriptionAdminListDto>>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public PageResponse<OrderSubscriptionListDto> orderSubscriptionListByMember(Pageable pageable,
                                                                                Long memberNo) {

        ResponseEntity<CommonResponseBody<PageResponse<OrderSubscriptionListDto>>> exchange =
            restTemplate.exchange(
                gatewayConfig.getGatewayServer() + ORDER_SUBSCRIPTION_MEMBER_LIST_API
                    + memberNo + String.format("?page=%d&size=%d", pageable.getPageNumber(),
                    pageable.getPageSize()),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsResponseDto(URI uri) {
        ResponseEntity<CommonResponseBody<List<OrderSubscriptionDetailsResponseDto>>> exchange =
            restTemplate.exchange(
                uri,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }

    public SuccessfulResponseDto deleteAndStockRollBack(URI toUri) {
        ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> exchange = restTemplate.exchange(toUri,
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<>() {
            }
        );

        return Objects.requireNonNull(exchange.getBody()).getResult();
    }
}
