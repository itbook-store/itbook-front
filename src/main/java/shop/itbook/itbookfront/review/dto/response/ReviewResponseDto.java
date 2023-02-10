package shop.itbook.itbookfront.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 리뷰 데이터를 받아올 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long orderProductNo;

    private Long productNo;

    private String productName;

    private Long memberNo;

    private Integer starPoint;

    private String content;

    private String image;
}
