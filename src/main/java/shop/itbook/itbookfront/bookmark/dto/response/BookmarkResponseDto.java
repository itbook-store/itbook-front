package shop.itbook.itbookfront.bookmark.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookfront.product.dto.response.ProductDetailsResponseDto;

/**
 * 즐겨찾기 응답 객체 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkResponseDto {

    private LocalDateTime bookmarkCreateAt;

    private ProductDetailsResponseDto productDetailsResponseDto;
}