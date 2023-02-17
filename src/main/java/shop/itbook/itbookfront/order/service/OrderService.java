package shop.itbook.itbookfront.order.service;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookfront.common.response.PageResponse;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookfront.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookfront.order.dto.response.OrderSubscriptionListDto;

/**
 * 주문 관련 비즈니스로직을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {
    PageResponse<OrderListMemberViewResponseDto> findOrderListOfMemberPageResponse(
        Pageable pageable, Long memberNo);

    PageResponse<OrderListAdminViewResponseDto> findOrderListAll(Pageable pageable);

    OrderPaymentDto addOrder(OrderAddRequestDto orderAddRequestDto,
                             Optional<Long> memberNo);

    OrderPaymentDto addOrderSubscription(OrderAddRequestDto orderAddRequestDto,
                                         Optional<Long> memberNo);

    void completeOrderSubscription(Long orderNo);

    OrderPaymentDto reOrder(OrderAddRequestDto orderAddRequestDto,
                            Long orderNo);

    OrderDetailsResponseDto findOrderDetails(Long orderNo);

    void cancelOrder(Long orderNo);

    /**
     * 주문 구매 확정 메서드입니다다.
     *
     * @param orderNo 주문 번호
     * @author 강명관
     */
    void orderPurchaseComplete(Long orderNo);

    /**
     * 관리자가 구독 주문 목록 조회하는 메서드 입니다.
     *
     * @param pageable 페이징 객체
     * @return 공용 페이징 객체 구독 주문 DTO
     * @author 강명관
     */
    PageResponse<OrderSubscriptionAdminListDto> orderSubscriptionListByAdmin(Pageable pageable);


    /**
     * 회원이 구독 주문 목록 조회하는 메서드 입니다.
     *
     * @param pageable 페이징 객체
     * @return 공용 페이징 객체 구독 주문 DTO
     * @author 강명관
     */
    PageResponse<OrderSubscriptionListDto> orderSubscriptionListByMember(Pageable pageable,
                                                                         Long memberNo);
}
