package shop.itbook.itbookfront.order.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문서 작성을 위한 Dto 입니다.
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

    private Boolean isSubscription;
    private Integer subscriptionPeriod;
}
