package shop.itbook.itbookfront.order.controller.serviceapi;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookfront.order.dto.request.OrderSheetFormDto;
import shop.itbook.itbookfront.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookfront.order.service.OrderService;

/**
 * 주문의 결제 처리를 위한 비동기 요청을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/async/orders")
@Slf4j
public class OrderAsyncController {

    private final OrderService orderService;

    /**
     * 결제 대기 상태의 주문 엔티티 인스턴스를 생성해서 데이터베이스에 값을 저장하고
     * 결제 요청을 하기 위한 정보를 생성합니다.
     *
     * @param orderAddRequestDto 주문 테이블에 저장할 정보
     * @param userDetailsDto     the user details dto
     * @return 결제 요청을 위한 정보를 담고 있는 Dto
     */
    @PostMapping("/payment-start")
    public OrderPaymentDto orderPaymentStart(
        @RequestBody
        OrderAddRequestDto orderAddRequestDto,
        @AuthenticationPrincipal
        UserDetailsDto userDetailsDto) {

        Optional<Long> memberNo = Optional.empty();

        if (Objects.nonNull(userDetailsDto)) {
            memberNo = Optional.of(userDetailsDto.getMemberNo());
        }

        return orderService.addOrder(orderAddRequestDto, memberNo);
    }

    /**
     * 결제 대기 상태의 주문의 결제를 재진행합니다.
     *
     * @param orderNo            the order no
     * @param orderAddRequestDto 주문 테이블에 저장할 정보
     * @return 결제 요청을 위한 정보를 담고 있는 Dto
     */
    @PostMapping("/payment-restart/{orderNo}")
    public OrderPaymentDto orderPaymentStart(@PathVariable("orderNo") Long orderNo,
                                             @RequestBody OrderAddRequestDto orderAddRequestDto) {

        return orderService.reOrder(orderAddRequestDto, orderNo);
    }

    /**
     * 구독 주문을 처리합니다.
     *
     * @param orderAddRequestDto 주문 테이블에 저장할 정보
     * @param userDetailsDto     the user details dto
     * @return 결제 요청을 위한 정보를 담고 있는 Dto
     */
    @PostMapping("/subscription/payment-start")
    public OrderPaymentDto orderSubscriptionPaymentStart(@RequestBody
                                                         OrderAddRequestDto orderAddRequestDto,
                                                         @AuthenticationPrincipal
                                                         UserDetailsDto userDetailsDto) {

        Optional<Long> memberNo = Optional.empty();

        if (Objects.nonNull(userDetailsDto)) {
            memberNo = Optional.of(userDetailsDto.getMemberNo());
        }

        return orderService.addOrderSubscription(orderAddRequestDto, memberNo);
    }

    /**
     * 구독 주문 결제 완료 후 비동기 요청을 처리합니다.
     *
     * @param orderNo 구독 주문 첫 번째 시퀀스의 주문 번호
     */
    @PostMapping("/subscription/payment-completion/{orderNo}")
    public void completeSubscriptionPayment(
        @PathVariable("orderNo") Long orderNo) {

        log.info("제발유");

        orderService.completeOrderSubscription(orderNo);
    }

    @PostMapping("/payment-cancel/{orderNo}")
    public void orderPaymentCancel(@PathVariable("orderNo") Long orderNo) {

        // TODO: 2023/02/11 주문 삭제 처리.
        orderService.cancelOrder(orderNo);
    }

    /**
     * 주문 상태 구매 확정 메서드 입니다.
     *
     * @param orderNo 주문번호
     * @author 강명관
     */
    @PostMapping("/purchase-complete/{orderNo}")
    public void orderPurchaseComplete(@PathVariable("orderNo") Long orderNo) {
        orderService.orderPurchaseComplete(orderNo);
    }
}
