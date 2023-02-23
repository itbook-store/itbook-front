package shop.itbook.itbookfront.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

/**
 * 주문 상세 조회를 위한 정보들을 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderDetailsResponseDto {

    private Long orderNo;
    private LocalDateTime orderStatusCreatedAt;
    private String orderStatus;
    private LocalDateTime orderCreatedAt;
    private Long amount;
    private Long deliveryFee;
    private Long deliveryNo;
    private String trackingNo;
    private String couponName;
    private Long totalCouponAmount;
    private Integer totalCouponPercent;
    private OrderDestinationDto orderDestinationDto;
    private List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList;
    private Long sellingAmount;
    private String nonMemberOrderCode;
    private Long couponAmount;
}
