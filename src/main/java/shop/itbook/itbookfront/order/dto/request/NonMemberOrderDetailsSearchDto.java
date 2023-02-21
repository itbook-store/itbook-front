package shop.itbook.itbookfront.order.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NonMemberOrderDetailsSearchDto {

    @Min(value = 1, message = "주문번호는 최솟값이 1입니다.")
    @NotNull
    private Long orderNo;

    @Length(min = 1, max = 36, message = "주문코드는 반드시 36자리 규칙을 지켜야합니다.")
    @NotBlank
    private String orderCode;
}
