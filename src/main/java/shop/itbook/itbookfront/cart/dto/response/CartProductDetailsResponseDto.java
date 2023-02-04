package shop.itbook.itbookfront.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDetailsResponseDto {
    private Integer productCount;

    private ProductDetailsResponseDto productDetailsResponseDto;
}
