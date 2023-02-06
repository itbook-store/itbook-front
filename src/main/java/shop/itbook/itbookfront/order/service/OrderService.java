package shop.itbook.itbookfront.order.service;

import org.springframework.data.domain.Pageable;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderAddResponseDto;
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

    OrderAddResponseDto addOrderOfMember(OrderAddRequestDto orderAddRequestDto, Long memberNo);

    void completeOrderPayOfMember(Long orderNo);
}
