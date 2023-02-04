package shop.itbook.itbookfront.pointhistory.dto.request;

import lombok.Getter;
import lombok.Setter;
import reactor.util.annotation.Nullable;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class PointHistorySearchRequestDto {

    @Nullable
    private String content;

    @Nullable
    private String searchWord;
}
