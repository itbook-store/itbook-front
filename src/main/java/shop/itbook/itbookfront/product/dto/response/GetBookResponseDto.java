package shop.itbook.itbookfront.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author 이하늬
 * @since 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetBookResponseDto {
    private Long productNo;

    private String productName;

    private String simpleDescription;

    private String detailsDescription;

    private Boolean isSelled;

    private Boolean isDeleted;

    private Integer stock;

//    private String category;

    private Integer increasePointPercent;

    private Long rawPrice;

    private Long fixedPrice;

    private Double discountPercent;

    private String fileThumbnailsUrl;
//    private MultipartFile fileThumbnails;

    private String isbn;

    private Integer pageCount;

    private String bookCreatedAt;

    private Boolean isEbook;

    private String fileEbookUrl;
//    private MultipartFile fileEbook;

    private String publisherName;

    private String authorName;

    private Long selledPrice;

    private String thumbnailsName;
}
