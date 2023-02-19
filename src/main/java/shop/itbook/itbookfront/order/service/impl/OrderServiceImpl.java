package shop.itbook.itbookfront.order.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.common.response.SuccessfulResponseDto;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.AsyncResponseDto;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookfront.order.service.OrderService;

/**
 * OrderService 인터페이스의 기본 구현체입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final GatewayConfig gatewayConfig;
    private final OrderAdaptor orderAdaptor;

    @Override
    public PageResponse<OrderListMemberViewResponseDto> findOrderListOfMemberPageResponse(
        Pageable pageable, Long memberNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path(String.format("/api/orders/list/%d", memberNo))
            .queryParam(String.format("page=%d&size=%d", pageable.getPageNumber(),
                pageable.getPageSize()))
            .build();

        return orderAdaptor.findOrderListViewResponseDtoPageResponse(uriComponents.toUri());
    }

    @Override
    public OrderPaymentDto addOrder(OrderAddRequestDto orderAddRequestDto,
                                    Optional<Long> memberNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/orders")
            .queryParamIfPresent("memberNo", memberNo)
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderAddRequestDto> http = new HttpEntity<>(orderAddRequestDto, headers);

        return orderAdaptor.addOrder(uriComponents.toUri(), http);
    }

    @Override
    public OrderPaymentDto addOrderSubscription(OrderAddRequestDto orderAddRequestDto,
                                                Optional<Long> memberNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/orders/subscription")
            .queryParamIfPresent("memberNo", memberNo)
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderAddRequestDto> http = new HttpEntity<>(orderAddRequestDto, headers);

        return orderAdaptor.addOrder(uriComponents.toUri(), http);
    }

    @Override
    public void completeOrderSubscription(Long orderNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/orders/subscription/completion")
            .queryParam("orderNo", orderNo)
            .build();

        orderAdaptor.postNullBodyReturnVoid(uriComponents.toUri());
    }

    @Override
    public void cancelOrder(Long orderNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path(String.format("/api/orders/cancel/%d", orderNo))
            .build();

        orderAdaptor.postNullBodyReturnVoid(uriComponents.toUri());
    }

    @Override
    public OrderDetailsResponseDto findOrderDetails(Long orderNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path(String.format("/api/orders/details/%d", orderNo))
            .build();

        return orderAdaptor.findOrderDetails(uriComponents.toUri());
    }

    @Override
    public PageResponse<OrderListAdminViewResponseDto> findOrderListAll(Pageable pageable) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path(String.format("/api/admin/orders/list"))
            .queryParam(String.format("page=%d&size=%d", pageable.getPageNumber(),
                pageable.getPageSize()))
            .build();

        return orderAdaptor.findOrderAdminListView(uriComponents.toUri());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void orderPurchaseComplete(Long orderNo) {
        orderAdaptor.orderPurchaseComplete(orderNo);
    }

    @Override
    public PageResponse<OrderSubscriptionAdminListDto> orderSubscriptionListByAdmin(Pageable pageable) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path(String.format("/api/admin/orders/list/subscription"))
            .queryParam(String.format("page=%d&size=%d", pageable.getPageNumber(),
                pageable.getPageSize()))
            .build();

        return orderAdaptor.orderSubscriptionListByAdmin(uriComponents.toUri());
    }

    @Override
    public PageResponse<OrderSubscriptionListDto> orderSubscriptionListByMember(Pageable pageable,
                                                                                Long memberNo) {

        PageResponse<OrderSubscriptionListDto> pageResponse =
            orderAdaptor.orderSubscriptionListByMember(pageable, memberNo);

        log.info("pageResponse {}", pageResponse);

        return pageResponse;
    }

    @Override
    public SuccessfulResponseDto deleteAndStockRollBack(Long orderNo) {

        UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(gatewayConfig.getGatewayServer())
            .path("/api/orders/" + orderNo + "/with-stock-rollback")
            .build();

        return orderAdaptor.deleteAndStockRollBack(uriComponents.toUri());
    }
}
