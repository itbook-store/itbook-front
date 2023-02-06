package shop.itbook.itbookfront.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author 노수연
 * @since 1.0
 */

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointSendRequestDto {

    @NotNull(message = "포인트 선물하는 회원 번호는 null 일 수 없습니다.")
    private Long sendMemberNo;

    @NotNull(message = "포인트 받는 회원 번호는 null 일 수 없습니다.")
    private Long receiveMemberNo;

    @NotNull(message = "포인트 받는 회원 아이디는 null 일 수 없습니다.")
    private String receiveMemberId;

    @Positive(message = "포인트는 양수여야 합니다.")
    private Long giftPoint;
}
