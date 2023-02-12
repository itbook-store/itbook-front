package shop.itbook.itbookfront.cart.dto.resquest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 장바구니 요청을 위한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartMemberRequestDto {

    @NotNull(message = "회원번호는 필수 입니다.")
    private Long memberNo;

    @NotNull(message = "상품번호는 필수 입니다.")
    private Long productNo;

    @NotNull(message = "수량은 필수 입니다.")
    @Positive(message = "수량은 음수일 수 없습니다.")
    private Integer productCount;


}
