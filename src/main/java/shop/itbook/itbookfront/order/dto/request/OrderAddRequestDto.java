package shop.itbook.itbookfront.order.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderAddRequestDto {

    private List<Long> productNoList;
    private List<Integer> productCntList;
    private String selectedDeliveryDate;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer postcode;
    private String roadNameAddress;
    private String recipientAddressDetails;
    private String orderId;
    private String orderName;
    private Long amount;
}