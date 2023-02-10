package shop.itbook.itbookfront.pointhistory.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class PointHistoryDetailsGiftResponseDto {

    private String mainMemberId;
    private String mainMemberName;
    private String subMemberId;
    private Long point;
    private Long remainedPoint;
    private LocalDateTime historyCreatedAt;
    private Boolean isDecrease;
}
