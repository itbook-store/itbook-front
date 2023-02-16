package shop.itbook.itbookfront.order.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 주문 등록을 위한 Dto 입니다.
 * 주문 서 작성 완료한 정보를 가지고 있습니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderAddRequestDto {

    private List<ProductDetailsDto> productDetailsDtoList;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate selectedDeliveryDate;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer postcode;
    private String roadNameAddress;
    private String recipientAddressDetails;
    private Long deliveryFee;
    private Long orderTotalCouponNo;
    private Long decreasePoint;
}
