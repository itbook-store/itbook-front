package shop.itbook.itbookfront.elastic.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
public class ProductSampleResponseDto {
    private Long productNo;
    private String name;
    private String thumbnailUrl;
    private Long fixedPrice;
    private Double increasePointPercent;
    private Double discountPercent;
    private Long rawPrice;
    private Boolean isForceSoldOut;
    private Integer stock;
    private Long selledPrice;

}
