package shop.itbook.itbookfront.member.dto.request;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "멤버상태명은 null값 및 공백을 허용하지 않습니다.")
    String memberStatusName;
}
