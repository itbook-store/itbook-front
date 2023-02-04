package shop.itbook.itbookfront.pointhistory.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class PointHistoryListDto {
    private Long memberNo;
    private String memberId;
    private String memberName;
    private Long pointHistoryNo;
    private Boolean isDecrease;
    private Long increaseDecreasePoint;
    private String content;
    private Long remainedPoint;
    private LocalDateTime historyCreatedAt;

}
