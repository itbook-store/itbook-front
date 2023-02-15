package shop.itbook.itbookfront.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니 저장에 대한 응답 객체
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartAddResponseDto {

    private boolean result;

    private String resultMessage;
}
