package shop.itbook.itbookfront.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자에게 보여주기 위해 상품의 정보를 계산하고 정보를 더욱 자세하게 나타낸 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderSheetProductDetailResponseDto {

    private String productName;
    private Long fixedPrice;
    private Long discountedPrice;
    private Integer productCount;
    private Long totalPrice;
}
