package shop.itbook.itbookfront.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 노수연
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateAdminRequestDto {
    String memberStatusName;
}
