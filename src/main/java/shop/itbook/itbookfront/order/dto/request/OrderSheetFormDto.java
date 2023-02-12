package shop.itbook.itbookfront.order.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 등록을 위한 Dto 입니다.
 * 주문서 작성이 완료됐을 경우 해당 정보를 등록합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderSheetFormDto {

    private List<Long> productNoList;
    private List<Integer> productCntList;
}
