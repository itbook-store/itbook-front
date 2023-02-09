package shop.itbook.itbookfront.bookmark.dto.request;

import javax.validation.constraints.NotNull;
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

    @NotNull(message = "회원번호는 필수 입니다.")
    Long memberNo;

    @NotNull(message = "상품번호는 필수 입니다.")
    Long productNo;
}