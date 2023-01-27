package shop.itbook.itbookfront.member.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class MemberStatusChangeRequestDto {

    private String memberStatusName;

    @Length(min=2, max = 255)
    @NotNull(message = "차단사유는 null값을 허용하지 않습니다.")
    private String statusChangedReason;

}
