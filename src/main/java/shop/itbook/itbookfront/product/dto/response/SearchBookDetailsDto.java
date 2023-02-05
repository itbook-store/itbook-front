package shop.itbook.itbookfront.product.dto.response;

import lombok.Data;

/**
 * @author 이하늬
 * @since 1.0
 */
@Data
public class SearchBookDetailsDto {

    private String title;
    private String author;
    private String pubDate;
    private String description;
    private Long priceStandard;
    private String publisher;
    private BookInfo subInfo;

    @Data
    public static class BookInfo {
        private String itemPage;
    }
}
