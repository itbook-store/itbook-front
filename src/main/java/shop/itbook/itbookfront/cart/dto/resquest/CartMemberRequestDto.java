package shop.itbook.itbookfront.cart.dto.resquest;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class CartMemberRequestDto {

    private Long memberNo;

    private Integer productNo;

    private Integer quantity;

    public CartMemberRequestDto(Long memberNo, Integer productNo) {
        this.memberNo = memberNo;
        this.productNo = productNo;
        this.quantity = 1;
    }
}
