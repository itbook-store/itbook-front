package shop.itbook.itbookfront.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDestinationResponseDto {

    Long recipientDestinationNo;

    String recipientName;

    String recipientPhoneNumber;

    Integer postcode;

    String roadNameAddress;

    String recipientAddressDetails;
}
