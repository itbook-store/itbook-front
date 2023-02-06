package shop.itbook.itbookfront.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookfront.order.service.OrderService;

/**
 * @author 정재원
 * @since 1.0
 */
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
}
