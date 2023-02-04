package shop.itbook.itbookfront.membership.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
public class MembershipResponseDto {

    private Integer membershipNo;
    private String membershipGrade;
    private Long membershipStandardAmount;
    private Long membershipPoint;

}
