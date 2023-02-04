package shop.itbook.itbookfront.coupon.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponIssueListResponseDto {

    private Long couponIssueNo;
    private String name;
    private String code;
    private Long amount;
    private Integer percent;
    private Long point;
    private String couponType;
    private String usageStatus;
    private LocalDateTime couponIssueCreatedAt;
    private LocalDateTime couponExpiredAt;
    private LocalDateTime couponUsageCreatedAt;
}
