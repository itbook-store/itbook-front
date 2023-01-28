package shop.itbook.itbookfront.order.dto.response;

import lombok.Getter;

/**
 * 주문에 필요한 정보를 가지고 있는 Dto
 * shop 서버에 요청해서 정보를 받아온다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderPaperResponseDto {

    private String productName;
    private Long fixedPrice;
    private Double discountPercent;

    // 배송지 목록 리스트로
}
