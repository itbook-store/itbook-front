package shop.itbook.itbookfront.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * 회원의 장바구니에대한 모든 정보를 갖고오는 DTO 클래스입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDetailsResponseDto {
    private Integer productCount;

    private ProductDetailsResponseDto productDetailsResponseDto;
}
