package shop.itbook.itbookfront.order.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자에게 보여주기 위해 더 자세한 정보를 가지고 있는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderSheetDetailResponseDto {
    private List<OrderSheetProductDetailResponseDto> orderSheetProductDetailResponseDtoList;
    private List<MemberDestinationResponseDto> memberDestinationResponseDtoList;

}
