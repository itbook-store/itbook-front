package shop.itbook.itbookfront.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자의 주문 목록 조회시 보여줄 정보 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class OrderListAdminViewResponseDto {

    private Long orderNo;
    private Long memberNo;
    private String orderStatus;
    private String recipientName;
    private String trackingNo;
}
