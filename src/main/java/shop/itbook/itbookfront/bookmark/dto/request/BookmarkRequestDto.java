package shop.itbook.itbookfront.bookmark.dto.request;

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
public class BookmarkRequestDto {

    Long memberNo;
    Long productNo;
}
