package shop.itbook.itbookfront.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구독 주문 목록 조회에 사용되는 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class OrderSubscriptionListDto {
    private Long orderNo;
    private String orderStatus;
    private String recipientName;
    private String recipientPhoneNumber;
    private String trackingNo;
    private Integer subscriptionPeriod;
}
