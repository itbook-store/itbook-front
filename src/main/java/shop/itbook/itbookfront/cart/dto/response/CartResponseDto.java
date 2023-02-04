package shop.itbook.itbookfront.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 강명관
 * @since 1.0
 */
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    private String fileThumbnailsUrl;
    private String productName;
    private Long fixedPrice;
    private Integer discountPercent;
    private Integer quantity;
    private Boolean isForceSoldOut;
    private Boolean isSubscription;
}
