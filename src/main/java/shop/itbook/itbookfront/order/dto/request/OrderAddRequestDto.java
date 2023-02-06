package shop.itbook.itbookfront.order.dto.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Queue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 정재원
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Setter
public class OrderAddRequestDto {

    private List<Long> productNoList;
    private List<Integer> productCntList;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate selectedDeliveryDate;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer postcode;
    private String roadNameAddress;
    private String recipientAddressDetails;

    @Override
    public String toString() {
        return "OrderAddRequestDto{" +
            "productNoList=" + productNoList +
            ", productCountList=" + productCntList +
            ", selectedDeliveryDate=" + selectedDeliveryDate +
            ", recipientName='" + recipientName + '\'' +
            ", recipientPhoneNumber='" + recipientPhoneNumber + '\'' +
            ", postcode=" + postcode +
            ", roadNameAddress='" + roadNameAddress + '\'' +
            ", recipientAddressDetails='" + recipientAddressDetails + '\'' +
            '}';
    }
}
