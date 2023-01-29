package shop.itbook.itbookfront.coupon.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class CouponInputRequestDto {
    @NotNull(message = "쿠폰 이름을 입력해 주세요.")
    @Length(min = 1, max = 20, message = "이름의 길이는 20자를 넘을수 없습니다.")
    private String name;

    @NotNull(message = "정액쿠폰의 금액을 입력해주세요.")
    @PositiveOrZero(message = "할인 금액이 음수일 수는 없습니다")
    private Long amount;

    @NotNull(message = "정률 쿠폰의 할인률을 입력해주세요.")
    @PositiveOrZero(message = "할인률이 음수일 수는 없습니다.")
    private Integer percent;

    @NotNull(message = "포인트 쿠폰의 지급포인트액을 입력해주세요.")
    @PositiveOrZero(message = "지급포인트액이 음수일 수는 없습니다.")
    private Long point;

    @PositiveOrZero(message = "최소할인 금액이 음수일 수는 없습니다.")
    private Long standardAmount;

    @Positive(message = "최대 할인금액이 음수거나 0원일수는 없습니다.")
    private Long maxDiscountAmount;

    @NotNull(message = "쿠폰 생성 가능 일을 입력해주세요.")
    private String couponCreatedAt;

    @NotNull(message = "쿠폰 정책의 만료일을 입력해주세요.")
    private String couponExpiredAt;

    private String image;

    @Positive(message = "쿠폰 발급수량이 음수 일 수는 없습니다.")
    private Long quantity;

//    @NotNull(message = "")
    private boolean isReserved;

}