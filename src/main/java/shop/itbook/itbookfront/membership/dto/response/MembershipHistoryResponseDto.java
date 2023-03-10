package shop.itbook.itbookfront.membership.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class MembershipHistoryResponseDto {

    private Long membershipHistoryNo;
    private Long monthlyUsageAmount;
    private LocalDateTime membershipHistoryCreatedAt;
    private Long memberNo;
    private Integer membershipNo;
    private String membershipGrade;
    private Integer memberStatusNo;
    private String nickname;
    private String name;
    private Boolean isMan;
    private LocalDateTime birth;
    private String phoneNumber;
    private String email;
    private LocalDateTime memberCreatedAt;

}
