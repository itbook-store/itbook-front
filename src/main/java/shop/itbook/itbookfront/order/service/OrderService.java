package shop.itbook.itbookfront.order.service;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;

/**
 * 주문 관련 비즈니스로직을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {
    PageResponse<OrderListMemberViewResponseDto> findOrderListOfMemberPageResponse(
        Pageable pageable, Long memberNo);

    PageResponse<OrderListMemberViewResponseDto> findOrderListAll(Pageable pageable);

    OrderPaymentDto addOrder(OrderAddRequestDto orderAddRequestDto,
                             Optional<Long> memberNo);

    OrderPaymentDto addOrderSubscription(OrderAddRequestDto orderAddRequestDto,
                                         Optional<Long> memberNo);

    void completeOrderSubscription(Long orderNo);

    OrderPaymentDto reOrder(OrderAddRequestDto orderAddRequestDto,
                            Long orderNo);

    OrderDetailsResponseDto findOrderDetails(Long orderNo);

    void cancelOrder(Long orderNo);
}
