package shop.itbook.itbookfront.member.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
public class MemberInfoResponseDto {
    private Long memberNo;
    private String memberId;
    private String membershipGrade;
    private String memberStatusName;
    private String nickname;
    private String name;
    private Boolean isMan;
    private LocalDateTime birth;
    private String password;
    private String phoneNumber;
    private String email;
    private LocalDateTime memberCreatedAt;
}
