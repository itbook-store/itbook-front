package shop.itbook.itbookfront.order.controller.serviceapi;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookfront.auth.dto.UserDetailsDto;
import shop.itbook.itbookfront.order.dto.request.OrderAddRequestDto;
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
@RequestMapping("async/orders")
@Slf4j
public class OrderAsyncController {

    private final OrderService orderService;

    /**
     * 결제 대기 상태의 오더 엔티티 인스턴스를 생성해서 데이터베이스에 값을 저장하고
     * 결제 요청을 하기 위한 정보를 생성합니다.
     *
     * @param orderAddRequestDto 주문 테이블에 저장할 정보
     * @return 결제 요청을 위한 정보를 담고 있는 Dto
     */
    @PostMapping("/payment-start")
    public OrderPaymentDto tempOrder(@RequestBody OrderAddRequestDto orderAddRequestDto,
                                     @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        log.info("제발제발제발: {}", orderAddRequestDto);

        Optional<Long> memberNo = Optional.empty();

        if (Objects.nonNull(userDetailsDto)) {
            memberNo = Optional.of(userDetailsDto.getMemberNo());
        }

        return orderService.addOrder(orderAddRequestDto, memberNo);
    }
}