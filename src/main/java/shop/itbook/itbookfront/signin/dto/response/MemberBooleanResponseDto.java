package shop.itbook.itbookfront.signin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 노수연
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberBooleanResponseDto {
    private Boolean isExists;
}
