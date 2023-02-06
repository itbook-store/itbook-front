package shop.itbook.itbookfront.order.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

/**
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderAddRequestDto {

    private List<Long> productNoList;
    private List<Integer> productCountList;
    private LocalDateTime selectedDeliveryDate;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer postcode;
    private String roadNameAddress;
    private String recipientAddressDetails;

    @Override
    public String toString() {
        return "OrderAddRequestDto{" +
            "productNoList=" + productNoList +
            ", productCountList=" + productCountList +
            ", selectedDeliveryDate=" + selectedDeliveryDate +
            ", recipientName='" + recipientName + '\'' +
            ", recipientPhoneNumber='" + recipientPhoneNumber + '\'' +
            ", postcode=" + postcode +
            ", roadNameAddress='" + roadNameAddress + '\'' +
            ", recipientAddressDetails='" + recipientAddressDetails + '\'' +
            '}';
    }
}
