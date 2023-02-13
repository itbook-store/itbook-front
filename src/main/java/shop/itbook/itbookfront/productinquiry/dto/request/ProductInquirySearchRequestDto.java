package shop.itbook.itbookfront.productinquiry.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquirySearchRequestDto {

    @NotNull(message = "카테고리 이름은 null일 수 없습니다.")
    private String categoryName;

    @NotNull(message = "검색조건은 null일 수 없습니다.")
    private String searchRequirement;

    @NotNull(message = "검색어는 null일 수 없습니다.")
    private String searchWord;
}
