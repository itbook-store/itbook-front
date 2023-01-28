package shop.itbook.itbookfront.coupon.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class CouponInputRequestDto {
    @NotNull(message = "쿠폰 이름을 입력해 주세요.")
    @Length(min = 1, max = 20, message = "")
    private String name;

//    @NotNull(message = "")
    @PositiveOrZero(message = "")
    private Long amount;

//    @NotNull(message = "")
    @PositiveOrZero(message = "")
    private Integer percent;

//    @NotNull(message = "")
    @PositiveOrZero(message = "")
    private Long point;

    @PositiveOrZero(message = "")
    private Long standardAmount;

    @PositiveOrZero(message = "")
    private Long maxDiscountAmount;

    @NotNull(message = "")
    private String couponCreatedAt;

    @NotNull(message = "")
    private String couponExpiredAt;

    private String image;

    private Long quantity;
//    @NotNull(message = "")
//    private boolean isReserved;

}
