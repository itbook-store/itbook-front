package shop.itbook.itbookfront.product.dto.request;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 상품을 등록하기 위한 데이터를 전달하는 requestDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ProductModifyRequestDto {

    @NotBlank(message = "상품 이름은 공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "상품 이름 길이는 1자-255자가 되어야 합니다.")
    private String productName;

    @NotBlank(message = "상세 설명은 공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "상세 설명 길이는 1자-255자가 되어야 합니다.")
    private String simpleDescription;

    private String detailsDescription;

    @NotNull(message = "재고는 반드시 입력되어야 합니다.")
    @PositiveOrZero(message = "재고는 0개 이상이어야 합니다.")
    private Integer stock;

    @NotNull(message = "카테고리를 선택해주세요.")
    private List<Integer> categoryNoList;

    @NotNull(message = "포인트 적용 여부는 반드시 선택되어야 합니다.")
    private Boolean isPointApplying;

    @Min(value = 0, message = "적립율은 0% 이상이어야 합니다.")
    @Max(value = 100, message = "적립율은 최대 100%입니다.")
    private Integer increasePointPercent;

    private Boolean isPointApplyingBasedSellingPrice;

    @NotNull(message = "정가는 반드시 입력되어야 합니다.")
    @PositiveOrZero(message = "정가는 0원 이상이어야 합니다.")
    private Long fixedPrice;

    @Min(value = 0, message = "할인율은 0% 이상이어야 합니다.")
    @Max(value = 100, message = "할인율은 최대 100%입니다.")
    private Double discountPercent;

    @NotNull(message = "원가는 반드시 입력되어야 합니다.")
    @Min(value = 0, message = "매입원가는 0원 이상이어야 합니다.")
    private Long rawPrice;

    @NotNull(message = "구독 여부는 반드시 선택되어야 합니다.")
    private Boolean isSubscription;
}
