package shop.itbook.itbookfront.order.service.nonmember.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookfront.config.GatewayConfig;
import shop.itbook.itbookfront.order.adaptor.OrderAdaptor;
import shop.itbook.itbookfront.order.dto.request.NonMemberOrderDetailsSearchDto;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookfront.order.service.nonmember.NonMemberOrderService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class NonMemberOrderServiceImpl implements NonMemberOrderService {

    private final OrderAdaptor orderAdaptor;
    private final GatewayConfig gatewayConfig;

    @Override
    public OrderDetailsResponseDto findNonMemberOrderDetails(
        NonMemberOrderDetailsSearchDto nonMemberOrderDetailsSearchDto) {

        String requestUrl = String.format("%s/api/orders/details/%d/non-member?orderCode=%s",
            gatewayConfig.getGatewayServer(),
            nonMemberOrderDetailsSearchDto.getOrderNo(),
            nonMemberOrderDetailsSearchDto.getOrderCode());

        return orderAdaptor.findNonMemberOrderDetails(requestUrl);
    }

    @Override
    public List<OrderSubscriptionDetailsResponseDto> findNonMemberSubscriptionOrderDetails(
        NonMemberOrderDetailsSearchDto nonMemberOrderDetailsSearchDto) {

        String requestUrl =
            String.format("%s/api/orders/details/%d/non-member/subscription?orderCode=%s",
                gatewayConfig.getGatewayServer(),
                nonMemberOrderDetailsSearchDto.getOrderNo(),
                nonMemberOrderDetailsSearchDto.getOrderCode());
        return orderAdaptor.findNonMemberSubscriptionOrderDetails(requestUrl);
    }
}
