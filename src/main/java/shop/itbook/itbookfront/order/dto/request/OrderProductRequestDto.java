package shop.itbook.itbookfront.order.dto.request;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * shop 서버에 주문에 필요한 정보드를 요청하는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequestDto {

    private Long productNo;
}
