package shop.itbook.itbookfront.product.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookfront.category.dto.response.CategoryNoResponseDto;


/**
 * @author 이하늬
 * @since 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetailsResponseDto {
    private Long productNo;

    private String productName;

    private String simpleDescription;

    private String detailsDescription;

    private Boolean isExposed;

    private Boolean isForceSoldOut;

    private Integer stock;

    private List<CategoryNoResponseDto> categoryList;

    private Integer increasePointPercent;

    private Long rawPrice;

    private Long fixedPrice;

    private Double discountPercent;

    private String fileThumbnailsUrl;

    private String isbn;

    private Integer pageCount;

    private String bookCreatedAt;

    private Boolean isEbook;

    private String fileEbookUrl;

    private String publisherName;

    private String authorName;

    private Long selledPrice;

    private String thumbnailsName;
}
