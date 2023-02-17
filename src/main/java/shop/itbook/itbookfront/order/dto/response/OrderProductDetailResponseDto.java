package shop.itbook.itbookfront.order.dto.response;

import lombok.Getter;

/**
 * 주문 상품 상세 조회를 위한 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderProductDetailResponseDto {
    private Long orderProductNo;
    private String productName;
    private Integer count;
    private Long productPrice;
    private String fileThumbnailsUrl;
}
