package shop.itbook.itbookfront.order.dto.response;

import java.util.List;
import lombok.Getter;
import shop.itbook.itbookfront.payment.dto.response.PaymentCardResponseDto;

/**
 * 주문 상세 조회를 위한 정보들을 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderDetailsResponseDto {
    List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList;
    List<OrderDestinationDto> orderDestinationDtoList;
    PaymentCardResponseDto paymentCardResponseDto;

    // TODO: 2023/02/10 포인트, 쿠폰 관련 정보
}
