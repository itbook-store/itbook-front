package shop.itbook.itbookfront.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 강명관
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    private String thumbnailUrl;
    private String productName;
    private Long fixedPrice;
    private Integer discountPercent;
    private Integer quantity;
    private boolean isForceSoldOut;
    private Boolean isSubscription;
}
