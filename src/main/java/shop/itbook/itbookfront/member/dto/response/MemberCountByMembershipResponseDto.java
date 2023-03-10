package shop.itbook.itbookfront.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCountByMembershipResponseDto {
    private Long commonCnt;
    private Long whiteCnt;
    private Long silverCnt;
    private Long goldCnt;
    private Long platinumCnt;
}
