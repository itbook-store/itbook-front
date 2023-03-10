package shop.itbook.itbookfront.order.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 정보 리스트에 나타내기 위한 정보를 담은 응답용 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class OrderListMemberViewResponseDto {

    private Long orderNo;
    private String orderStatus;
    private LocalDateTime orderCreatedAt;
    private String recipientName;
    private String recipientPhoneNumber;
    private String trackingNo;
}
